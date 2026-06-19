package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 璁㈠崟鏄庣粏琛?鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface OrderItemMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 鏍规嵁OrderItemId鏇存柊
     */
    Integer updateByOrderItemId(@Param("bean") T t, @Param("orderItemId") String orderItemId);


    /**
     * 鏍规嵁OrderItemId鍒犻櫎
     */
    Integer deleteByOrderItemId(@Param("orderItemId") String orderItemId);


    /**
     * 鏍规嵁OrderItemId鑾峰彇瀵硅薄
     */
    T selectByOrderItemId(@Param("orderItemId") String orderItemId);


}

