package com.aimall.service;

import com.aimall.entity.po.ProductSku;
import com.aimall.entity.query.ProductSkuQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ProductSkuVO;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛
 */
public interface ProductSkuService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<ProductSku> findListByParam(ProductSkuQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(ProductSkuQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<ProductSku> findListByPage(ProductSkuQuery param);

    /**
     * 鏂板
     */
    Integer add(ProductSku bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<ProductSku> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<ProductSku> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(ProductSku bean, ProductSkuQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(ProductSkuQuery param);

    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash鏌ヨ瀵硅薄
     */
    ProductSku getProductSkuByProductIdAndPropertyValueIdHash(String productId, String propertyValueIdHash);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash淇敼
     */
    Integer updateProductSkuByProductIdAndPropertyValueIdHash(ProductSku bean, String productId, String propertyValueIdHash);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash鍒犻櫎
     */
    Integer deleteProductSkuByProductIdAndPropertyValueIdHash(String productId, String propertyValueIdHash);

    void updateStock(String productId, String propertyValueIdHash, Integer changeStock);

    PaginationResultVO<ProductSkuVO> findListByPage4ListVO(ProductSkuQuery param);
}
