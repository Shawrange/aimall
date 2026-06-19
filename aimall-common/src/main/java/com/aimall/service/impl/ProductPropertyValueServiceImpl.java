package com.aimall.service.impl;

import com.aimall.entity.enums.PageSize;
import com.aimall.entity.po.ProductPropertyValue;
import com.aimall.entity.query.ProductPropertyValueQuery;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.mappers.ProductPropertyValueMapper;
import com.aimall.service.ProductPropertyValueService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *  涓氬姟鎺ュ彛瀹炵幇
 */
@Service("productPropertyValueService")
public class ProductPropertyValueServiceImpl implements ProductPropertyValueService {

	@Resource
	private ProductPropertyValueMapper<ProductPropertyValue, ProductPropertyValueQuery> productPropertyValueMapper;

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	@Override
	public List<ProductPropertyValue> findListByParam(ProductPropertyValueQuery param) {
		return this.productPropertyValueMapper.selectList(param);
	}

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	@Override
	public Integer findCountByParam(ProductPropertyValueQuery param) {
		return this.productPropertyValueMapper.selectCount(param);
	}

	/**
	 * 鍒嗛〉鏌ヨ鏂规硶
	 */
	@Override
	public PaginationResultVO<ProductPropertyValue> findListByPage(ProductPropertyValueQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<ProductPropertyValue> list = this.findListByParam(param);
		PaginationResultVO<ProductPropertyValue> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 鏂板
	 */
	@Override
	public Integer add(ProductPropertyValue bean) {
		return this.productPropertyValueMapper.insert(bean);
	}

	/**
	 * 鎵归噺鏂板
	 */
	@Override
	public Integer addBatch(List<ProductPropertyValue> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productPropertyValueMapper.insertBatch(listBean);
	}

	/**
	 * 鎵归噺鏂板鎴栬€呬慨鏀?
	 */
	@Override
	public Integer addOrUpdateBatch(List<ProductPropertyValue> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.productPropertyValueMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 澶氭潯浠舵洿鏂?
	 */
	@Override
	public Integer updateByParam(ProductPropertyValue bean, ProductPropertyValueQuery param) {
		StringTools.checkParam(param);
		return this.productPropertyValueMapper.updateByParam(bean, param);
	}

	/**
	 * 澶氭潯浠跺垹闄?
	 */
	@Override
	public Integer deleteByParam(ProductPropertyValueQuery param) {
		StringTools.checkParam(param);
		return this.productPropertyValueMapper.deleteByParam(param);
	}

	/**
	 * 鏍规嵁ProductIdAndPropertyValueId鑾峰彇瀵硅薄
	 */
	@Override
	public ProductPropertyValue getProductPropertyValueByProductIdAndPropertyValueId(String productId, String propertyValueId) {
		return this.productPropertyValueMapper.selectByProductIdAndPropertyValueId(productId, propertyValueId);
	}

	/**
	 * 鏍规嵁ProductIdAndPropertyValueId淇敼
	 */
	@Override
	public Integer updateProductPropertyValueByProductIdAndPropertyValueId(ProductPropertyValue bean, String productId, String propertyValueId) {
		return this.productPropertyValueMapper.updateByProductIdAndPropertyValueId(bean, productId, propertyValueId);
	}

	/**
	 * 鏍规嵁ProductIdAndPropertyValueId鍒犻櫎
	 */
	@Override
	public Integer deleteProductPropertyValueByProductIdAndPropertyValueId(String productId, String propertyValueId) {
		return this.productPropertyValueMapper.deleteByProductIdAndPropertyValueId(productId, propertyValueId);
	}
}
