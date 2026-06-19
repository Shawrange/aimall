package com.aimall.service;

import com.aimall.entity.dto.PayInfoDTO;
import com.aimall.entity.dto.PayOrderNotifyDTO;
import com.aimall.entity.dto.PostOrderDTO;
import com.aimall.entity.enums.OrderStatusEnum;
import com.aimall.entity.enums.PayChannelEnum;
import com.aimall.entity.po.OrderInfo;
import com.aimall.entity.query.OrderInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 璁㈠崟淇℃伅 涓氬姟鎺ュ彛
 */
public interface OrderInfoService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<OrderInfo> findListByParam(OrderInfoQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(OrderInfoQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<OrderInfo> findListByPage(OrderInfoQuery param);

    /**
     * 鏂板
     */
    Integer add(OrderInfo bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<OrderInfo> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<OrderInfo> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(OrderInfo bean, OrderInfoQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(OrderInfoQuery param);

    /**
     * 鏍规嵁OrderId鏌ヨ瀵硅薄
     */
    OrderInfo getOrderInfoByOrderId(String orderId);


    /**
     * 鏍规嵁OrderId淇敼
     */
    Integer updateOrderInfoByOrderId(OrderInfo bean, String orderId);


    /**
     * 鏍规嵁OrderId鍒犻櫎
     */
    Integer deleteOrderInfoByOrderId(String orderId);

    PayInfoDTO postOrder(String userId, PostOrderDTO postOrderDTO);

    void payNotify(PayChannelEnum payChannelEnum, Map<String, String> requestParams, String jsonBody);

    void payOrderSuccess(PayOrderNotifyDTO payOrderNotifyDTO);

    PayInfoDTO getPayInfo(String userId, String orderId);

    void cancelOrder(String userId, String orderId, OrderStatusEnum orderStatusEnum);

    void deleteOrder(String userId, String orderId);

    void confirmOrder(String userId, String orderId);

    void refundByOrderItemId(String userId, String orderItemId);

    void refundByOrderId(String userId, String orderId);

    BigDecimal getOrderTotalAmount(String orderTime, Integer[] orderStatus);
}
