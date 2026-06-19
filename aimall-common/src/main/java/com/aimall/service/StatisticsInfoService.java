package com.aimall.service;

import com.aimall.entity.po.StatisticsInfo;
import com.aimall.entity.query.StatisticsInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.StatisticsDataVO;

import java.util.List;


/**
 * 鏁版嵁缁熻缁撴灉 涓氬姟鎺ュ彛
 */
public interface StatisticsInfoService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<StatisticsInfo> findListByParam(StatisticsInfoQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(StatisticsInfoQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<StatisticsInfo> findListByPage(StatisticsInfoQuery param);

    /**
     * 鏂板
     */
    Integer add(StatisticsInfo bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<StatisticsInfo> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<StatisticsInfo> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(StatisticsInfo bean, StatisticsInfoQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(StatisticsInfoQuery param);

    /**
     * 鏍规嵁StatisticsDateAndDataType鏌ヨ瀵硅薄
     */
    StatisticsInfo getStatisticsInfoByStatisticsDateAndDataType(String statisticsDate, Integer dataType);


    /**
     * 鏍规嵁StatisticsDateAndDataType淇敼
     */
    Integer updateStatisticsInfoByStatisticsDateAndDataType(StatisticsInfo bean, String statisticsDate, Integer dataType);


    /**
     * 鏍规嵁StatisticsDateAndDataType鍒犻櫎
     */
    Integer deleteStatisticsInfoByStatisticsDateAndDataType(String statisticsDate, Integer dataType);

    void statisticsData(String date);

    List<StatisticsDataVO> loadWeeklyStatisticsData();
}
