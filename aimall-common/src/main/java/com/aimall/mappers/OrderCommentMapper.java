package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 *  鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface OrderCommentMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 鏍规嵁OrderId鏇存柊
	 */
	 Integer updateByOrderId(@Param("bean") T t,@Param("orderId") String orderId);


	/**
	 * 鏍规嵁OrderId鍒犻櫎
	 */
	 Integer deleteByOrderId(@Param("orderId") String orderId);


	/**
	 * 鏍规嵁OrderId鑾峰彇瀵硅薄
	 */
	 T selectByOrderId(@Param("orderId") String orderId);


}

