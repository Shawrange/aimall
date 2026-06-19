package com.aimall.task;

import com.aimall.constants.Constants;
import com.aimall.entity.dto.RagDataDTO;
import com.aimall.entity.enums.ExecutorServiceSingletonEnum;
import com.aimall.entity.enums.ProductStatusEnum;
import com.aimall.entity.enums.RagDataTypeEnum;
import com.aimall.entity.po.ProductInfo;
import com.aimall.entity.po.ProductPropertyValue;
import com.aimall.entity.po.ProductSku;
import com.aimall.entity.po.RagQuestion;
import com.aimall.entity.query.ProductInfoQuery;
import com.aimall.entity.query.ProductPropertyValueQuery;
import com.aimall.entity.query.ProductSkuQuery;
import com.aimall.mappers.ProductInfoMapper;
import com.aimall.mappers.ProductPropertyValueMapper;
import com.aimall.mappers.ProductSkuMapper;
import com.aimall.service.RagQuestionService;
import com.aimall.utils.JsonUtils;
import com.aimall.utils.StringTools;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RagDataTask {

    @Resource
    private VectorStore vectorStore;

    @Resource
    private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

    @Resource
    private ProductPropertyValueMapper<ProductPropertyValue, ProductPropertyValueQuery> productPropertyValueMapper;

    @Resource
    private ProductSkuMapper<ProductSku, ProductSkuQuery> productSkuMapper;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RagQuestionService ragQuestionService;

    @PostConstruct
    public void subscribe() {
        ExecutorServiceSingletonEnum.INSTANCE.getExecutorService().execute(() -> {
            RBlockingQueue<RagDataDTO> queue = redissonClient.getBlockingQueue(Constants.REDIS_QUEUE_RAG_DATA, JsonJacksonCodec.INSTANCE);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 闃诲鑾峰彇娑堟伅
                    RagDataDTO message = queue.take();
                    log.info("娑堣垂娑堟伅: {}", JsonUtils.convertObj2Json(message));
                    RagDataTypeEnum typeEnum = RagDataTypeEnum.getByType(message.getType());
                    switch (typeEnum) {
                        case PRODUCT:
                            saveData2VectorDB4Product(message.getDataId());
                            break;
                        case FAQ:
                            saveData2VectorDB4FAQ(message.getDataId());
                            break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.info("娑堟伅娑堣垂琚腑鏂?);
                    break;
                } catch (Exception e) {
                    log.error("澶勭悊娑堟伅澶辫触", e);
                }
            }
        });
    }

    //灏嗗晢鍝佷俊鎭洿鏂板埌鍚戦噺鏁版嵁搴?
    private void saveData2VectorDB4Product(String productId) {
        ProductInfo productInfo = this.productInfoMapper.selectByProductId(productId);
        if (!ProductStatusEnum.ON_SALE.getStatus().equals(productInfo.getStatus())) {
            vectorStore.delete(RagDataTypeEnum.PRODUCT.getType() + productId);
            return;
        }
        ProductPropertyValueQuery productPropertyValueQuery = new ProductPropertyValueQuery();
        productPropertyValueQuery.setProductId(productId);
        productPropertyValueQuery.setOrderBy("property_sort asc");
        List<ProductPropertyValue> productPropertyValueList = this.productPropertyValueMapper.selectList(productPropertyValueQuery);

        Map<String, ProductPropertyValue> productPropertyValueMap = productPropertyValueList.stream().collect(Collectors.toMap(item -> item.getPropertyValueId(),
                Function.identity(), (data1, data2) -> data2));

        ProductSkuQuery productSkuQuery = new ProductSkuQuery();
        productSkuQuery.setProductId(productId);
        List<ProductSku> allSkuList = productSkuMapper.selectList(productSkuQuery);

        List<Document> list = new ArrayList<>();

        //涓嶅甫sku淇℃伅
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("dataType", RagDataTypeEnum.PRODUCT.getType());
        metaData.put("productId", productInfo.getProductId());
        metaData.put("productName", productInfo.getProductName());
        Document doc = new Document(productInfo.getProductId(), productInfo.getProductName(), metaData);
        list.add(doc);

        List<Map<String, Object>> skuList = new ArrayList<>();
        for (ProductSku sku : allSkuList) {
            metaData = new HashMap<>();
            metaData.put("dataType", RagDataTypeEnum.PRODUCT.getType());
            metaData.put("productId", productInfo.getProductId());
            metaData.put("productName", productInfo.getProductName());
            StringBuilder content = new StringBuilder();
            content.append(productInfo.getProductName()).append(",");
            Map<String, Object> skuProperty = new HashMap<>();
            String[] propertyValueIdArray = sku.getPropertyValueIds().split("-");
            StringBuffer skuContent = new StringBuffer();
            Integer index = 0;
            for (String propertyValueId : propertyValueIdArray) {
                index++;
                ProductPropertyValue propertyValue = productPropertyValueMap.get(propertyValueId);
                skuContent = skuContent.append(propertyValue.getPropertyName()).append("锛?).append(propertyValue.getPropertyValue());
                if (index < propertyValueIdArray.length) {
                    skuContent.append(",");
                }
            }
            content.append(skuContent);
            skuProperty.put("property", skuContent);
            skuList.add(skuProperty);
            metaData.put("skuList", skuList);
            doc = new Document(productInfo.getProductId() + "_" + sku.getPropertyValueIdHash(), content.toString(), metaData);
            list.add(doc);
            //鐧剧偧涓€娆″彧鑳藉鐞?0涓?
            if (list.size() >= 10) {
                vectorStore.add(list);
                list.clear();
            }
        }

        if (!list.isEmpty()) {
            vectorStore.add(list);
        }
    }

    private void saveData2VectorDB4FAQ(String questionId) {
        RagQuestion ragQuestion = ragQuestionService.getRagQuestionByQuestionId(Integer.parseInt(questionId));
        if (ragQuestion == null) {
            vectorStore.delete(RagDataTypeEnum.FAQ.getType() + questionId);
            return;
        }
        List<Document> list = new ArrayList<>();
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("dataType", RagDataTypeEnum.FAQ.getType());
        metaData.put("questionId", ragQuestion.getQuestionId());
        metaData.put("question", ragQuestion.getQuestion());
        metaData.put("answer", ragQuestion.getAnswer());
        if (!StringTools.isEmpty(ragQuestion.getSimilarQuestion())) {
            metaData.put("similarQuestion", ragQuestion.getSimilarQuestion());
        } else {
            metaData.put("similarQuestion", "鏆傛棤鐩镐技闂硶");
        }

        StringBuilder content = new StringBuilder();
        // 鏍稿績闂
        content.append(ragQuestion.getQuestion()).append(" ");
        // 鐩镐技闂锛堝鏋滄湁锛?
        if (!StringTools.isEmpty(ragQuestion.getSimilarQuestion())) {
            content.append("鐩镐技闂锛?).append(ragQuestion.getSimilarQuestion()).append(" ");
        }
        // 绛旀
        content.append("绛旀鏄細").append(ragQuestion.getAnswer());
        Document doc = new Document(questionId, content.toString(), metaData);
        list.add(doc);
        vectorStore.add(list);
    }
}
