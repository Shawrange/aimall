package com.aimall.service.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aimall.entity.enums.PageSize;
import com.aimall.entity.query.OrderItemQuery;
import com.aimall.entity.po.OrderItem;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.query.SimplePage;
import com.aimall.mappers.OrderItemMapper;
import com.aimall.service.OrderItemService;
import com.aimall.utils.StringTools;


/**
 * 璁㈠崟鏄庣粏琛?涓氬姟鎺ュ彛瀹炵幇
 */
@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {

	@Resource
	private OrderItemMapper<OrderItem, OrderItemQuery> orderItemMapper;

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	@Override
	public List<OrderItem> findListByParam(OrderItemQuery param) {
		return this.orderItemMapper.selectList(param);
	}

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	@Override
	public Integer findCountByParam(OrderItemQuery param) {
		return this.orderItemMapper.selectCount(param);
	}

	/**
	 * 鍒嗛〉鏌ヨ鏂规硶
	 */
	@Override
	public PaginationResultVO<OrderItem> findListByPage(OrderItemQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<OrderItem> list = this.findListByParam(param);
		PaginationResultVO<OrderItem> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 鏂板
	 */
	@Override
	public Integer add(OrderItem bean) {
		return this.orderItemMapper.insert(bean);
	}

	/**
	 * 鎵归噺鏂板
	 */
	@Override
	public Integer addBatch(List<OrderItem> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.orderItemMapper.insertBatch(listBean);
	}

	/**
	 * 鎵归噺鏂板鎴栬€呬慨鏀?
	 */
	@Override
	public Integer addOrUpdateBatch(List<OrderItem> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.orderItemMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 澶氭潯浠舵洿鏂?
	 */
	@Override
	public Integer updateByParam(OrderItem bean, OrderItemQuery param) {
		StringTools.checkParam(param);
		return this.orderItemMapper.updateByParam(bean, param);
	}

	/**
	 * 澶氭潯浠跺垹闄?
	 */
	@Override
	public Integer deleteByParam(OrderItemQuery param) {
		StringTools.checkParam(param);
		return this.orderItemMapper.deleteByParam(param);
	}

	/**
	 * 鏍规嵁OrderItemId鑾峰彇瀵硅薄
	 */
	@Override
	public OrderItem getOrderItemByOrderItemId(String orderItemId) {
		return this.orderItemMapper.selectByOrderItemId(orderItemId);
	}

	/**
	 * 鏍规嵁OrderItemId淇敼
	 */
	@Override
	public Integer updateOrderItemByOrderItemId(OrderItem bean, String orderItemId) {
		return this.orderItemMapper.updateByOrderItemId(bean, orderItemId);
	}

	/**
	 * 鏍规嵁OrderItemId鍒犻櫎
	 */
	@Override
	public Integer deleteOrderItemByOrderItemId(String orderItemId) {
		return this.orderItemMapper.deleteByOrderItemId(orderItemId);
	}
}
