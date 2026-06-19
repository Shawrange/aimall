package com.aimall.service;

import com.aimall.entity.po.OrderComment;
import com.aimall.entity.query.OrderCommentQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛
 */
public interface OrderCommentService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<OrderComment> findListByParam(OrderCommentQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(OrderCommentQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<OrderComment> findListByPage(OrderCommentQuery param);

    /**
     * 鏂板
     */
    Integer add(OrderComment bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<OrderComment> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<OrderComment> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(OrderComment bean, OrderCommentQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(OrderCommentQuery param);

    /**
     * 鏍规嵁OrderId鏌ヨ瀵硅薄
     */
    OrderComment getOrderCommentByOrderId(String orderId);


    /**
     * 鏍规嵁OrderId淇敼
     */
    Integer updateOrderCommentByOrderId(OrderComment bean, String orderId);


    /**
     * 鏍规嵁OrderId鍒犻櫎
     */
    Integer deleteOrderCommentByOrderId(String orderId);

    void postComment(String userId, String orderId, String commentContent, String commentImages, Integer star);

    void postReComment(String userId, String orderId, String reCommentContent, String reCommentImages);

    void postBizComment(String orderId, String commentBizReply);
}
