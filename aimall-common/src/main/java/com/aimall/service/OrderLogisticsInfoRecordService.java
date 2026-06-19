package com.aimall.service;

import java.util.List;

import com.aimall.entity.query.OrderLogisticsInfoRecordQuery;
import com.aimall.entity.po.OrderLogisticsInfoRecord;
import com.aimall.entity.vo.PaginationResultVO;


/**
 *  涓氬姟鎺ュ彛
 */
public interface OrderLogisticsInfoRecordService {

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	List<OrderLogisticsInfoRecord> findListByParam(OrderLogisticsInfoRecordQuery param);

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	Integer findCountByParam(OrderLogisticsInfoRecordQuery param);

	/**
	 * 鍒嗛〉鏌ヨ
	 */
	PaginationResultVO<OrderLogisticsInfoRecord> findListByPage(OrderLogisticsInfoRecordQuery param);

	/**
	 * 鏂板
	 */
	Integer add(OrderLogisticsInfoRecord bean);

	/**
	 * 鎵归噺鏂板
	 */
	Integer addBatch(List<OrderLogisticsInfoRecord> listBean);

	/**
	 * 鎵归噺鏂板/淇敼
	 */
	Integer addOrUpdateBatch(List<OrderLogisticsInfoRecord> listBean);

	/**
	 * 澶氭潯浠舵洿鏂?
	 */
	Integer updateByParam(OrderLogisticsInfoRecord bean,OrderLogisticsInfoRecordQuery param);

	/**
	 * 澶氭潯浠跺垹闄?
	 */
	Integer deleteByParam(OrderLogisticsInfoRecordQuery param);

	/**
	 * 鏍规嵁RecordId鏌ヨ瀵硅薄
	 */
	OrderLogisticsInfoRecord getOrderLogisticsInfoRecordByRecordId(Integer recordId);


	/**
	 * 鏍规嵁RecordId淇敼
	 */
	Integer updateOrderLogisticsInfoRecordByRecordId(OrderLogisticsInfoRecord bean,Integer recordId);


	/**
	 * 鏍规嵁RecordId鍒犻櫎
	 */
	Integer deleteOrderLogisticsInfoRecordByRecordId(Integer recordId);

}
