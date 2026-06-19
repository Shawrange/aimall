package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface ProductInfoMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 鏍规嵁ProductId鏇存柊
     */
    Integer updateByProductId(@Param("bean") T t, @Param("productId") String productId);


    /**
     * 鏍规嵁ProductId鍒犻櫎
     */
    Integer deleteByProductId(@Param("productId") String productId);


    /**
     * 鏍规嵁ProductId鑾峰彇瀵硅薄
     */
    T selectByProductId(@Param("productId") String productId);

    void updateProdctuctTotalSale(@Param("productId") String productId, @Param("changeCount") Integer changeCount);

}

