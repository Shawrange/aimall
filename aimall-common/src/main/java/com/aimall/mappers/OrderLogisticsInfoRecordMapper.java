package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 *  鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface OrderLogisticsInfoRecordMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 鏍规嵁RecordId鏇存柊
	 */
	 Integer updateByRecordId(@Param("bean") T t,@Param("recordId") Integer recordId);


	/**
	 * 鏍规嵁RecordId鍒犻櫎
	 */
	 Integer deleteByRecordId(@Param("recordId") Integer recordId);


	/**
	 * 鏍规嵁RecordId鑾峰彇瀵硅薄
	 */
	 T selectByRecordId(@Param("recordId") Integer recordId);


}

