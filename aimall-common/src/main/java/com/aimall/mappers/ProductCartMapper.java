package com.aimall.mappers;

import com.aimall.entity.po.ProductCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 璐墿杞?鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface ProductCartMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 鏍规嵁CartId鏇存柊
     */
    Integer updateByCartId(@Param("bean") T t, @Param("cartId") String cartId);


    /**
     * 鏍规嵁CartId鍒犻櫎
     */
    Integer deleteByCartId(@Param("cartId") String cartId);


    /**
     * 鏍规嵁CartId鑾峰彇瀵硅薄
     */
    T selectByCartId(@Param("cartId") String cartId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId鏇存柊
     */
    Integer updateByProductIdAndPropertyValueIdHashAndUserId(@Param("bean") T t, @Param("productId") String productId,
                                                             @Param("propertyValueIdHash") String propertyValueIdHash, @Param("userId") String userId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId鍒犻櫎
     */
    Integer deleteByProductIdAndPropertyValueIdHashAndUserId(@Param("productId") String productId, @Param("propertyValueIdHash") String propertyValueIdHash, @Param(
            "userId") String userId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId鑾峰彇瀵硅薄
     */
    T selectByProductIdAndPropertyValueIdHashAndUserId(@Param("productId") String productId, @Param("propertyValueIdHash") String propertyValueIdHash,
                                                       @Param("userId") String userId);


    void updateCartBuyCount(@Param("cartId") String cartId, @Param("buyCount") Integer buyCount);

    void deleteBatch(@Param("cartList") List<ProductCart> cartList);

}

