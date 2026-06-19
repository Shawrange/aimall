package com.aimall.mappers;

import com.aimall.entity.po.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface ProductSkuMapper<T, P> extends BaseMapper<T, P> {


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash鏇存柊
     */
    Integer updateByProductIdAndPropertyValueIdHash(@Param("bean") T t, @Param("productId") String productId, @Param("propertyValueIdHash") String propertyValueIdHash);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash鍒犻櫎
     */
    Integer deleteByProductIdAndPropertyValueIdHash(@Param("productId") String productId, @Param("propertyValueIdHash") String propertyValueIdHash);


    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHash鑾峰彇瀵硅薄
     */
    T selectByProductIdAndPropertyValueIdHash(@Param("productId") String productId, @Param("propertyValueIdHash") String propertyValueIdHash);


    void deleteBatch(@Param("productId") String productId, @Param("dataList") List<T> dataList);

    void updateBatch(@Param("productId") String productId, @Param("dataList") List<T> dataList);

    Integer updateStock(@Param("productId") String productId, @Param("propertyValueIdHash") String propertyValueIdHash, @Param("changeStock") Integer changeStock);

    Integer updateStockBatch(@Param("orderItemList") List<OrderItem> orderItemList);
}

