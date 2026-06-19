package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 鏁版嵁缁熻缁撴灉 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface StatisticsInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 鏍规嵁StatisticsDateAndDataType鏇存柊
	 */
	 Integer updateByStatisticsDateAndDataType(@Param("bean") T t,@Param("statisticsDate") String statisticsDate,@Param("dataType") Integer dataType);


	/**
	 * 鏍规嵁StatisticsDateAndDataType鍒犻櫎
	 */
	 Integer deleteByStatisticsDateAndDataType(@Param("statisticsDate") String statisticsDate,@Param("dataType") Integer dataType);


	/**
	 * 鏍规嵁StatisticsDateAndDataType鑾峰彇瀵硅薄
	 */
	 T selectByStatisticsDateAndDataType(@Param("statisticsDate") String statisticsDate,@Param("dataType") Integer dataType);


}

