package com.aimall.service;

import java.util.List;

import com.aimall.entity.query.ProductPropertyValueQuery;
import com.aimall.entity.po.ProductPropertyValue;
import com.aimall.entity.vo.PaginationResultVO;


/**
 *  涓氬姟鎺ュ彛
 */
public interface ProductPropertyValueService {

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	List<ProductPropertyValue> findListByParam(ProductPropertyValueQuery param);

	/**
	 * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
	 */
	Integer findCountByParam(ProductPropertyValueQuery param);

	/**
	 * 鍒嗛〉鏌ヨ
	 */
	PaginationResultVO<ProductPropertyValue> findListByPage(ProductPropertyValueQuery param);

	/**
	 * 鏂板
	 */
	Integer add(ProductPropertyValue bean);

	/**
	 * 鎵归噺鏂板
	 */
	Integer addBatch(List<ProductPropertyValue> listBean);

	/**
	 * 鎵归噺鏂板/淇敼
	 */
	Integer addOrUpdateBatch(List<ProductPropertyValue> listBean);

	/**
	 * 澶氭潯浠舵洿鏂?
	 */
	Integer updateByParam(ProductPropertyValue bean,ProductPropertyValueQuery param);

	/**
	 * 澶氭潯浠跺垹闄?
	 */
	Integer deleteByParam(ProductPropertyValueQuery param);

	/**
	 * 鏍规嵁ProductIdAndPropertyValueId鏌ヨ瀵硅薄
	 */
	ProductPropertyValue getProductPropertyValueByProductIdAndPropertyValueId(String productId,String propertyValueId);


	/**
	 * 鏍规嵁ProductIdAndPropertyValueId淇敼
	 */
	Integer updateProductPropertyValueByProductIdAndPropertyValueId(ProductPropertyValue bean,String productId,String propertyValueId);


	/**
	 * 鏍规嵁ProductIdAndPropertyValueId鍒犻櫎
	 */
	Integer deleteProductPropertyValueByProductIdAndPropertyValueId(String productId,String propertyValueId);

}
