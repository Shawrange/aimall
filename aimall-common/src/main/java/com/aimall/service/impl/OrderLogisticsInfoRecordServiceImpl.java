package com.aimall.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aimall.entity.enums.PageSize;
import com.aimall.entity.query.OrderLogisticsInfoRecordQuery;
import com.aimall.entity.po.OrderLogisticsInfoRecord;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.query.SimplePage;
import com.aimall.mappers.OrderLogisticsInfoRecordMapper;
import com.aimall.service.OrderLogisticsInfoRecordService;
import com.aimall.utils.StringTools;


/**
 *  涓氬姟鎺ュ彛瀹炵幇
 */
@Service("orderLogisticsInfoRecordService")
public class OrderLogisticsInfoRecordServiceImpl implements OrderLogisticsInfoRecordService {

	@Resource
	private OrderLogisticsInfoRecordMapper<OrderLogisticsInfoRecord, OrderLogisticsInfoRecordQuery> orderLogisticsInfoRecordMapper;

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	@Override
	public List<OrderLogisticsInfoRecord> findListByParam(OrderLogisticsInfoRecordQuery param) {
		return this.orderLogisticsInfoRecordMapper.selectList(param);
	}

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	@Override
	public Integer findCountByParam(OrderLogisticsInfoRecordQuery param) {
		return this.orderLogisticsInfoRecordMapper.selectCount(param);
	}

	/**
	 * 鍒嗛〉鏌ヨ鏂规硶
	 */
	@Override
	public PaginationResultVO<OrderLogisticsInfoRecord> findListByPage(OrderLogisticsInfoRecordQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<OrderLogisticsInfoRecord> list = this.findListByParam(param);
		PaginationResultVO<OrderLogisticsInfoRecord> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 鏂板
	 */
	@Override
	public Integer add(OrderLogisticsInfoRecord bean) {
		return this.orderLogisticsInfoRecordMapper.insert(bean);
	}

	/**
	 * 鎵归噺鏂板
	 */
	@Override
	public Integer addBatch(List<OrderLogisticsInfoRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.orderLogisticsInfoRecordMapper.insertBatch(listBean);
	}

	/**
	 * 鎵归噺鏂板鎴栬€呬慨鏀?
	 */
	@Override
	public Integer addOrUpdateBatch(List<OrderLogisticsInfoRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.orderLogisticsInfoRecordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 澶氭潯浠舵洿鏂?
	 */
	@Override
	public Integer updateByParam(OrderLogisticsInfoRecord bean, OrderLogisticsInfoRecordQuery param) {
		StringTools.checkParam(param);
		return this.orderLogisticsInfoRecordMapper.updateByParam(bean, param);
	}

	/**
	 * 澶氭潯浠跺垹闄?
	 */
	@Override
	public Integer deleteByParam(OrderLogisticsInfoRecordQuery param) {
		StringTools.checkParam(param);
		return this.orderLogisticsInfoRecordMapper.deleteByParam(param);
	}

	/**
	 * 鏍规嵁RecordId鑾峰彇瀵硅薄
	 */
	@Override
	public OrderLogisticsInfoRecord getOrderLogisticsInfoRecordByRecordId(Integer recordId) {
		return this.orderLogisticsInfoRecordMapper.selectByRecordId(recordId);
	}

	/**
	 * 鏍规嵁RecordId淇敼
	 */
	@Override
	public Integer updateOrderLogisticsInfoRecordByRecordId(OrderLogisticsInfoRecord bean, Integer recordId) {
		return this.orderLogisticsInfoRecordMapper.updateByRecordId(bean, recordId);
	}

	/**
	 * 鏍规嵁RecordId鍒犻櫎
	 */
	@Override
	public Integer deleteOrderLogisticsInfoRecordByRecordId(Integer recordId) {
		return this.orderLogisticsInfoRecordMapper.deleteByRecordId(recordId);
	}
}
