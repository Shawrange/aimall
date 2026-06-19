package com.aimall.service;

import com.aimall.entity.po.ProductCart;
import com.aimall.entity.query.ProductCartQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ProductSkuVO;

import java.util.List;


/**
 * 璐墿杞?涓氬姟鎺ュ彛
 */
public interface ProductCartService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<ProductCart> findListByParam(ProductCartQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(ProductCartQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<ProductCart> findListByPage(ProductCartQuery param);

    /**
     * 鏂板
     */
    Integer add(ProductCart bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<ProductCart> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<ProductCart> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(ProductCart bean, ProductCartQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(ProductCartQuery param);

    /**
     * 鏍规嵁CartId鏌ヨ瀵硅薄
     */
    ProductCart getProductCartByCartId(String cartId);


    /**
     * 鏍规嵁CartId淇敼
     */
    Integer updateProductCartByCartId(ProductCart bean, String cartId);


    /**
     * 鏍规嵁CartId鍒犻櫎
     */
    Integer deleteProductCartByCartId(String cartId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId鏌ヨ瀵硅薄
     */
    ProductCart getProductCartByProductIdAndPropertyValueIdHashAndUserId(String productId, String propertyValueIdHash, String userId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId淇敼
     */
    Integer updateProductCartByProductIdAndPropertyValueIdHashAndUserId(ProductCart bean, String productId, String propertyValueIdHash, String userId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId鍒犻櫎
     */
    Integer deleteProductCartByProductIdAndPropertyValueIdHashAndUserId(String productId, String propertyValueIdHash, String userId);

    void add2Cart(ProductCart cart);

    PaginationResultVO<ProductSkuVO> loadProductCart(ProductCartQuery query);
}
