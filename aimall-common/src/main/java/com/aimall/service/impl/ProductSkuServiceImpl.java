package com.aimall.service.impl;

import com.aimall.entity.enums.PageSize;
import com.aimall.entity.po.ProductInfo;
import com.aimall.entity.po.ProductPropertyValue;
import com.aimall.entity.po.ProductSku;
import com.aimall.entity.query.ProductInfoQuery;
import com.aimall.entity.query.ProductPropertyValueQuery;
import com.aimall.entity.query.ProductSkuQuery;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ProductSkuProperDataVO;
import com.aimall.entity.vo.ProductSkuVO;
import com.aimall.exception.BusinessException;
import com.aimall.mappers.ProductInfoMapper;
import com.aimall.mappers.ProductPropertyValueMapper;
import com.aimall.mappers.ProductSkuMapper;
import com.aimall.service.ProductSkuService;
import com.aimall.utils.CopyTools;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("productSkuService")
public class ProductSkuServiceImpl implements ProductSkuService {

    @Resource
    private ProductSkuMapper<ProductSku, ProductSkuQuery> productSkuMapper;

    @Resource
    private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

    @Resource
    private ProductPropertyValueMapper<ProductPropertyValue, ProductPropertyValueQuery> productPropertyValueMapper;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<ProductSku> findListByParam(ProductSkuQuery param) {
        return this.productSkuMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(ProductSkuQuery param) {
        return this.productSkuMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<ProductSku> findListByPage(ProductSkuQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ProductSku> list = this.findListByParam(param);
        PaginationResultVO<ProductSku> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(ProductSku bean) {
        return this.productSkuMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<ProductSku> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.productSkuMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<ProductSku> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.productSkuMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(ProductSku bean, ProductSkuQuery param) {
        StringTools.checkParam(param);
        return this.productSkuMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(ProductSkuQuery param) {
        StringTools.checkParam(param);
        return this.productSkuMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash鑾峰彇瀵硅薄
     */
    @Override
    public ProductSku getProductSkuByProductIdAndPropertyValueIdHash(String productId, String propertyValueIdHash) {
        return this.productSkuMapper.selectByProductIdAndPropertyValueIdHash(productId, propertyValueIdHash);
    }

    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash淇敼
     */
    @Override
    public Integer updateProductSkuByProductIdAndPropertyValueIdHash(ProductSku bean, String productId, String propertyValueIdHash) {
        return this.productSkuMapper.updateByProductIdAndPropertyValueIdHash(bean, productId, propertyValueIdHash);
    }

    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash鍒犻櫎
     */
    @Override
    public Integer deleteProductSkuByProductIdAndPropertyValueIdHash(String productId, String propertyValueIdHash) {
        return this.productSkuMapper.deleteByProductIdAndPropertyValueIdHash(productId, propertyValueIdHash);
    }

    @Override
    public void updateStock(String productId, String propertyValueIdHash, Integer changeStock) {
        Integer changeCount = this.productSkuMapper.updateStock(productId, propertyValueIdHash, changeStock);
        if (changeCount == 0) {
            throw new BusinessException("搴撳瓨涓嶈冻");
        }
    }


    @Override
    public PaginationResultVO<ProductSkuVO> findListByPage4ListVO(ProductSkuQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ProductSku> list = this.findListByParam(param);
        if (list.isEmpty()) {
            return new PaginationResultVO<>(0, param.getPageSize(), param.getPageNo(), 0, new ArrayList<>());
        }
        List<String> productIdList = list.stream().map(ProductSku::getProductId).toList();
        //鏌ヨ鍟嗗搧淇℃伅
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductIdList(productIdList);
        List<ProductInfo> productInfoList = productInfoMapper.selectList(productInfoQuery);

        Map<String, ProductInfo> tempProductInfoMap = productInfoList.stream().collect(Collectors.toMap(item -> item.getProductId(), Function.identity(), (data1,
                                                                                                                                                           data2) -> data2));
        //鏌ヨ鍟嗗搧灞炴€т俊鎭?
        ProductPropertyValueQuery productPropertyValueQuery = new ProductPropertyValueQuery();
        productPropertyValueQuery.setProductIdList(productIdList);
        List<ProductPropertyValue> productPropertyValueList = productPropertyValueMapper.selectList(productPropertyValueQuery);
        Map<String, ProductPropertyValue> productPropertyValueMap =
                productPropertyValueList.stream().collect(Collectors.toMap(item -> item.getProductId() + item.getPropertyValueId(), Function.identity(),
                        (data1, data2) -> data2));

        List<ProductSkuVO> productSkuVOList = new ArrayList<>();
        for (ProductSku sku : list) {
            ProductSkuVO productSkuVO = CopyTools.copy(sku, ProductSkuVO.class);
            String propertyValueIds = sku.getPropertyValueIds();
            String[] propertyValueIdArray = propertyValueIds.split("-");
            List<ProductSkuProperDataVO> propertyData = new ArrayList<>();
            String cover = null;
            for (String propertyValueId : propertyValueIdArray) {
                ProductSkuProperDataVO productSkuProperDataVO = new ProductSkuProperDataVO();
                ProductPropertyValue productPropertyValue = productPropertyValueMap.get(sku.getProductId() + propertyValueId);
                if (productPropertyValue == null) {
                    continue;
                }
                productSkuProperDataVO.setPropertyName(productPropertyValue.getPropertyName());
                productSkuProperDataVO.setPropertyValue(productPropertyValue.getPropertyValue());
                propertyData.add(productSkuProperDataVO);

                if (cover == null && !StringTools.isEmpty(productPropertyValue.getPropertyCover())) {
                    cover = productPropertyValue.getPropertyCover();
                }
            }
            productSkuVO.setPropertyData(propertyData);
            ProductInfo productInfo = tempProductInfoMap.get(sku.getProductId());
            productSkuVO.setProductName(productInfo.getProductName());
            //璁剧疆灏侀潰
            cover = cover == null ? productInfo.getCover().split(",")[0] : cover;
            productSkuVO.setProductCover(cover);
            productSkuVOList.add(productSkuVO);
        }
        PaginationResultVO<ProductSkuVO> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), productSkuVOList);
        return result;
    }
}
