package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 璁㈠崟淇℃伅 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface OrderInfoMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 鏍规嵁OrderId鏇存柊
     */
    Integer updateByOrderId(@Param("bean") T t, @Param("orderId") String orderId);


    /**
     * 鏍规嵁OrderId鍒犻櫎
     */
    Integer deleteByOrderId(@Param("orderId") String orderId);


    /**
     * 鏍规嵁OrderId鑾峰彇瀵硅薄
     */
    T selectByOrderId(@Param("orderId") String orderId);


    BigDecimal selectOrderTotalAmount(@Param("orderTime") String orderTime, @Param("orderStatus") Integer[] orderStatus);

    Integer updateOrderStatusBatch(@Param("orderStatus") Integer orderStatus, @Param("oldStatus") Integer oldStatus,
                                   @Param("orderIdList") List<String> orderIdList);

}

