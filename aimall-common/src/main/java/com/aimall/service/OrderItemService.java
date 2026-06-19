package com.aimall.service;

import java.util.List;

import com.aimall.entity.query.OrderItemQuery;
import com.aimall.entity.po.OrderItem;
import com.aimall.entity.vo.PaginationResultVO;


/**
 * 璁㈠崟鏄庣粏琛?涓氬姟鎺ュ彛
 */
public interface OrderItemService {

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	List<OrderItem> findListByParam(OrderItemQuery param);

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	Integer findCountByParam(OrderItemQuery param);

	/**
	 * 鍒嗛〉鏌ヨ
	 */
	PaginationResultVO<OrderItem> findListByPage(OrderItemQuery param);

	/**
	 * 鏂板
	 */
	Integer add(OrderItem bean);

	/**
	 * 鎵归噺鏂板
	 */
	Integer addBatch(List<OrderItem> listBean);

	/**
	 * 鎵归噺鏂板/淇敼
	 */
	Integer addOrUpdateBatch(List<OrderItem> listBean);

	/**
	 * 澶氭潯浠舵洿鏂?
	 */
	Integer updateByParam(OrderItem bean,OrderItemQuery param);

	/**
	 * 澶氭潯浠跺垹闄?
	 */
	Integer deleteByParam(OrderItemQuery param);

	/**
	 * 鏍规嵁OrderItemId鏌ヨ瀵硅薄
	 */
	OrderItem getOrderItemByOrderItemId(String orderItemId);


	/**
	 * 鏍规嵁OrderItemId淇敼
	 */
	Integer updateOrderItemByOrderItemId(OrderItem bean,String orderItemId);


	/**
	 * 鏍规嵁OrderItemId鍒犻櫎
	 */
	Integer deleteOrderItemByOrderItemId(String orderItemId);

}
