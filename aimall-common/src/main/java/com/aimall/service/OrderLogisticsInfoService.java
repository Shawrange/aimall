package com.aimall.service;

import com.aimall.entity.po.OrderLogisticsInfo;
import com.aimall.entity.query.OrderLogisticsInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 鐗╂祦淇℃伅琛?涓氬姟鎺ュ彛
 */
public interface OrderLogisticsInfoService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<OrderLogisticsInfo> findListByParam(OrderLogisticsInfoQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(OrderLogisticsInfoQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<OrderLogisticsInfo> findListByPage(OrderLogisticsInfoQuery param);

    /**
     * 鏂板
     */
    Integer add(OrderLogisticsInfo bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<OrderLogisticsInfo> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<OrderLogisticsInfo> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(OrderLogisticsInfo bean, OrderLogisticsInfoQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(OrderLogisticsInfoQuery param);

    /**
     * 鏍规嵁OrderId鏌ヨ瀵硅薄
     */
    OrderLogisticsInfo getOrderLogisticsInfoByOrderId(String orderId);


    /**
     * 鏍规嵁OrderId淇敼
     */
    Integer updateOrderLogisticsInfoByOrderId(OrderLogisticsInfo bean, String orderId);


    /**
     * 鏍规嵁OrderId鍒犻櫎
     */
    Integer deleteOrderLogisticsInfoByOrderId(String orderId);

    void delivery(OrderLogisticsInfo logisticsInfo);

    //妯℃嫙鐗╂祦
    void mockOrderlogistics(String orderId);

    OrderLogisticsInfo getOrderLogisticsRecords(String userId, String orderId);
}
