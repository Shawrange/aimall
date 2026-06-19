package com.aimall.controller;

import com.aimall.annotation.GlobalInterceptor;
import com.aimall.component.ChatComponent;
import com.aimall.component.RedisComponent;
import com.aimall.entity.enums.PromptTypeEnum;
import com.aimall.entity.enums.RagDataTypeEnum;
import com.aimall.entity.po.AgentMessage;
import com.aimall.entity.po.ProductInfo;
import com.aimall.entity.po.ProductPropertyValue;
import com.aimall.entity.po.ProductSku;
import com.aimall.entity.query.AgentMessageQuery;
import com.aimall.entity.query.ProductPropertyValueQuery;
import com.aimall.entity.query.ProductSkuQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.service.AgentMessageService;
import com.aimall.service.ProductInfoService;
import com.aimall.service.ProductPropertyValueService;
import com.aimall.service.ProductSkuService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agent")
@Slf4j
public class AgentController extends ABaseController {

    @Resource
    private ChatComponent chatComponent;

    @Resource
    private AgentMessageService agentMessageService;

    @RequestMapping("/sendMessage")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO sendMessage(String message) {
        AgentMessage agentMessage = chatComponent.sendMessage(getTokenUserInfo().getUserId(), message);
        return getSuccessResponseVO(agentMessage);
    }


    @RequestMapping("/cancelMessage")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO cancelMessage(Integer messageId) {
        chatComponent.cancelMessage(getTokenUserInfo().getUserId(), messageId);
        return getSuccessResponseVO(null);
    }


    @RequestMapping("/loadHistoryMessage")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadHistoryMessage(Integer pageNo, Integer maxMessageId) {
        AgentMessageQuery agentMessageQuery = new AgentMessageQuery();
        agentMessageQuery.setOrderBy("message_id desc");
        agentMessageQuery.setPageNo(pageNo);
        agentMessageQuery.setUserId(getTokenUserInfo().getUserId());
        agentMessageQuery.setMaxMessageId(maxMessageId);
        PaginationResultVO resultVO = agentMessageService.findListByPage(agentMessageQuery);
        return getSuccessResponseVO(resultVO);
    }

    @Resource
    private VectorStore vectorStore;

    @RequestMapping("/test")
    public ResponseVO test() {
        SearchRequest request =
                SearchRequest.builder().query("鎴戣涔颁竴鍙岀敺闉?).topK(5).build();
        List<Document> docs = vectorStore.similaritySearch(request);
        return getSuccessResponseVO(docs);
    }

    private String buildTypeFilter(String dataType) {
        return String.format("dataType == '%s'", dataType);
    }

    @Resource
    private ProductInfoService productInfoService;

    @Resource
    private ProductPropertyValueService productPropertyValueService;

    @Resource
    private ProductSkuService productSkuService;

    @RequestMapping("/testadd")
    public ResponseVO testadd() {

        ProductInfo productInfo = this.productInfoService.getProductInfoByProductId("153133309154815");

        ProductPropertyValueQuery productPropertyValueQuery = new ProductPropertyValueQuery();
        productPropertyValueQuery.setProductId(productInfo.getProductId());
        productPropertyValueQuery.setOrderBy("property_sort asc");
        List<ProductPropertyValue> productPropertyValueList = this.productPropertyValueService.findListByParam(productPropertyValueQuery);

        Map<String, ProductPropertyValue> productPropertyValueMap = productPropertyValueList.stream().collect(Collectors.toMap(item -> item.getPropertyValueId(),
                Function.identity(), (data1, data2) -> data2));

        ProductSkuQuery productSkuQuery = new ProductSkuQuery();
        productSkuQuery.setProductId(productInfo.getProductId());
        List<ProductSku> allSkuList = productSkuService.findListByParam(productSkuQuery);

        List<Document> list = new ArrayList<>();


        //涓嶅甫sku淇℃伅
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("dataType", RagDataTypeEnum.PRODUCT.getType());
        metaData.put("productId", productInfo.getProductId());
        metaData.put("productName", productInfo.getProductName());
        StringBuilder content = new StringBuilder();
        content.append(productInfo.getProductName()).append("銆?);
        Document doc = new Document(productInfo.getProductId(), productInfo.getProductName(), metaData);
        list.add(doc);

        List<Map<String, Object>> skuList = new ArrayList<>();

        for (ProductSku sku : allSkuList) {
            content = new StringBuilder();
            metaData = new HashMap<>();
            metaData.put("dataType", RagDataTypeEnum.PRODUCT.getType());
            metaData.put("productId", productInfo.getProductId());
            metaData.put("productName", productInfo.getProductName());
            content.append("鍟嗗搧鍚嶇О锛?).append(productInfo.getProductName()).append("銆?);
            Map<String, Object> skuProperty = new HashMap<>();
            String[] propertyValueIdArray = sku.getPropertyValueIds().split("-");
            StringBuffer skuContent = new StringBuffer();
            Integer index = 0;
            for (String propertyValueId : propertyValueIdArray) {
                index++;
                ProductPropertyValue propertyValue = productPropertyValueMap.get(propertyValueId);
                // content.append(propertyValue.getPropertyValue()).append(" ");
                skuContent = skuContent.append(propertyValue.getPropertyName()).append("锛?).append(propertyValue.getPropertyValue());
                if (index < propertyValueIdArray.length) {
                    skuContent.append("锛?);
                }
            }
            content.append(skuContent);
            skuProperty.put("property", skuContent);
            skuList.add(skuProperty);
            metaData.put("skuList", skuList);
            doc = new Document(productInfo.getProductId() + sku.getPropertyValueIdHash(), content.toString(), metaData);
            list.add(doc);
        }
        // metaData = new HashMap<>();
        // metaData.put("dataType", RagDataTypeEnum.PRODUCT.getType());

        vectorStore.add(list);
        return getSuccessResponseVO(null);
    }


    @RequestMapping("/testrag")
    public ResponseVO testrag() {

        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder().build());
        return getSuccessResponseVO(documents);
    }

    @Resource
    private ChatClient chatClient;

    @RequestMapping("/testMessage")
    public ResponseVO testMessage() {
        String systemPrompt = getPrompt(PromptTypeEnum.GLOBAL, null, null, null);
        String prompt = """
                    璇峰垎鏋愮敤鎴锋秷鎭殑璐墿鎰忓浘,骞舵彁鍙栧叧閿俊鎭€?
                    鐢ㄦ埛闂: %s
                
                    璇蜂粠浠ヤ笅鎰忓浘绫诲瀷涓€夋嫨鏈€鍖归厤鐨勪竴涓細
                    INTENT TYPES:
                    - PRODUCT_SEARCH: 鎼滅储鍟嗗搧銆佹兂涔颁笢瑗裤€佹煡鐪嬪晢鍝佷俊鎭紙濡傦細鎴戞兂涔版墜鏈猴紝鎵句竴涓嬭繛琛ｈ锛?
                    - QUERY_ORDER: 璁㈠崟鏌ヨ锛堝锛氭垜鐨勮鍗曪紵锛?濡傜敤鎴锋彁渚涜鍗曞彿锛堜緥濡傦細20251229161636SGUCPYYI1TXOEPHY锛夛紝灏嗚鍗曞彿瑙ｆ瀽鍑烘潵鐢╠ata杩斿洖,濡傛灉鏃犳硶瑙ｆ瀽璁㈠崟濂斤紝涓嶈鍐峝ata涓繑鍥?
                    - REFUND: 閫€娆鹃€€璐х敵璇凤紙濡傦細鎴戞兂閫€娆撅紝鍟嗗搧鏈夐棶棰樿閫€璐э級
                    - CANCEL_ORDER: 鍙栨秷宸蹭笅鍗曚絾鏈彂璐ф垨鏈畬鎴愮殑璁㈠崟锛堝锛氭垜涓嶆兂瑕佷簡锛屽府鎴戝彇娑堣鍗曪紝鍙栨秷鎴戝垰涔扮殑閭ｄ釜涓滆タ锛?
                    - CONFIRM_RECEIPT: 鐢ㄦ埛纭宸叉敹鍒拌揣鐗╋紙濡傦細鎴戝凡缁忔敹鍒拌揣浜嗭紝鐐瑰摢閲岀‘璁ゆ敹璐э紝閭ｄ釜涓滆タ鎴戞嬁鍒颁簡锛?
                    - QUERY_LOGISTICS: 鐗╂祦鏌ヨ銆佸揩閫掕窡韪€佸寘瑁圭姸鎬佹煡璇紙濡傦細鎴戠殑蹇€掑埌鍝簡锛熸煡涓€涓嬬墿娴侊紝璺熻釜鍖呰９锛?
                    - PRODUCT_REVIEW: 鐢ㄦ埛涓诲姩瀵瑰凡鏀惰揣鍟嗗搧缁欏嚭璇勪环锛堝锛?缁欎笂涓鍗曞ソ璇?銆?杩欎笢瑗垮お宸簡锛屾垜瑕佽瘎浠?銆?缁欎竴涓ソ璇勫惂"銆?缁欎竴涓樊璇?锛?
                    - CHAT: 涓€鑸€ч棶棰橈紝姣斿 闂€欍€佹墦鎷涘懠锛屽晢鍝佸姣旓紝璐墿缁嗗垯绛?
                      杩斿洖JSON鏍煎紡锛屽寘鍚互涓嬪瓧娈碉細
                    - intentType: 鎰忓浘绫诲瀷锛堜娇鐢ㄤ笂闈㈢殑绫诲瀷鍊硷級
                    - orderId: 璁㈠崟鍙凤紙濡傛灉鍙互鎻愬彇灏辨彁鍙栵紝濡傛灉鏃犳硶鎻愬彇锛屽氨涓嶈繑鍥烇紝涓嶈闅忎究鎹忛€犺鍗曞彿锛岃鍗曞彿绫讳技杩欐牱:20251229161636SGUCPYYI1TXOEPHY锛?
                    绀轰緥鍝嶅簲锛?
                    {
                      "intentType": "PRODUCT_SEARCH",
                      "orderId":"20251229161636SGUCPYYI1TXOEPHY"
                    }
                """;
        prompt = String.format(prompt, "鎴戞兂涔颁竴鍓€佺綏鐪熻抗 鐧介笩鏈濆嚖鍥?);

        UserIntent userIntent = chatClient.prompt().system(systemPrompt).user(prompt).call().entity(UserIntent.class);

        return getSuccessResponseVO(userIntent);
    }

    record UserIntent(String intent, String data) {

    }

    @Resource
    private RedisComponent redisComponent;

    private String getPrompt(PromptTypeEnum promptType, String userId, String message, String ragData) {
        String prompt = redisComponent.getPrompt(promptType.getKey());
        prompt = prompt == null ? promptType.getPrompt() : prompt;
        if (PromptTypeEnum.CHAT == promptType) {
            ragData = StringTools.isEmpty(ragData) ? "鐭ヨ瘑搴撲腑鏆傛棤鏆傛棤鐩稿叧鍐呭" : ragData;
            prompt = String.format(prompt, ragData, userId, message);
            return prompt;
        }
        if (PromptTypeEnum.GLOBAL != promptType) {
            prompt = String.format(prompt, userId, message);
            return prompt;
        }
        return prompt;
    }
}

