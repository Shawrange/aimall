package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface ProductPropertyValueMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 鏍规嵁ProductIdAndPropertyValueId鏇存柊
     */
    Integer updateByProductIdAndPropertyValueId(@Param("bean") T t, @Param("productId") String productId, @Param("propertyValueId") String propertyValueId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueId鍒犻櫎
     */
    Integer deleteByProductIdAndPropertyValueId(@Param("productId") String productId, @Param("propertyValueId") String propertyValueId);


    /**
     * 鏍规嵁ProductIdAndPropertyValueId鑾峰彇瀵硅薄
     */
    T selectByProductIdAndPropertyValueId(@Param("productId") String productId, @Param("propertyValueId") String propertyValueId);


    void deleteBatch(@Param("productId") String productId, @Param("dataList") List<T> dataList);

    void updateBatch(@Param("productId") String productId, @Param("dataList") List<T> dataList);

}

