package com.aimall.service.impl;

import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.entity.enums.OrderStatusEnum;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.enums.StatisticsDataTypeEnum;
import com.aimall.entity.po.StatisticsInfo;
import com.aimall.entity.query.OrderInfoQuery;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.query.StatisticsInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.StatisticsDataVO;
import com.aimall.mappers.StatisticsInfoMapper;
import com.aimall.service.OrderInfoService;
import com.aimall.service.StatisticsInfoService;
import com.aimall.utils.DateUtil;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 鏁版嵁缁熻缁撴灉 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("statisticsInfoService")
public class StatisticsInfoServiceImpl implements StatisticsInfoService {

    @Resource
    private StatisticsInfoMapper<StatisticsInfo, StatisticsInfoQuery> statisticsInfoMapper;

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<StatisticsInfo> findListByParam(StatisticsInfoQuery param) {
        return this.statisticsInfoMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(StatisticsInfoQuery param) {
        return this.statisticsInfoMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<StatisticsInfo> findListByPage(StatisticsInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<StatisticsInfo> list = this.findListByParam(param);
        PaginationResultVO<StatisticsInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(StatisticsInfo bean) {
        return this.statisticsInfoMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<StatisticsInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.statisticsInfoMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<StatisticsInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.statisticsInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(StatisticsInfo bean, StatisticsInfoQuery param) {
        StringTools.checkParam(param);
        return this.statisticsInfoMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(StatisticsInfoQuery param) {
        StringTools.checkParam(param);
        return this.statisticsInfoMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁StatisticsDateAndDataType鑾峰彇瀵硅薄
     */
    @Override
    public StatisticsInfo getStatisticsInfoByStatisticsDateAndDataType(String statisticsDate, Integer dataType) {
        return this.statisticsInfoMapper.selectByStatisticsDateAndDataType(statisticsDate, dataType);
    }

    /**
     * 鏍规嵁StatisticsDateAndDataType淇敼
     */
    @Override
    public Integer updateStatisticsInfoByStatisticsDateAndDataType(StatisticsInfo bean, String statisticsDate, Integer dataType) {
        return this.statisticsInfoMapper.updateByStatisticsDateAndDataType(bean, statisticsDate, dataType);
    }

    /**
     * 鏍规嵁StatisticsDateAndDataType鍒犻櫎
     */
    @Override
    public Integer deleteStatisticsInfoByStatisticsDateAndDataType(String statisticsDate, Integer dataType) {
        return this.statisticsInfoMapper.deleteByStatisticsDateAndDataType(statisticsDate, dataType);
    }

    @Override
    public List<StatisticsDataVO> loadWeeklyStatisticsData() {
        StatisticsInfoQuery query = new StatisticsInfoQuery();
        String startDate = DateUtil.getBeforeDay(8, DateTimePatternEnum.YYYY_MM_DD.getPattern());
        String endDate = DateUtil.getBeforeDay(1, DateTimePatternEnum.YYYY_MM_DD.getPattern());
        query.setStatisticsDateStart(startDate);
        query.setStatisticsDateEnd(endDate);
        query.setOrderBy("statistics_date asc,data_type asc");
        List<StatisticsInfo> statisticsInfoList = statisticsInfoMapper.selectList(query);

        Map<String, StatisticsInfo> statisticsInfoMap = statisticsInfoList.stream().collect(Collectors.toMap(item -> item.getStatisticsDate() + item.getDataType(),
                Function.identity(), (data1, data2) -> data2));
        List<String> dateList = DateUtil.getDateRange(startDate, endDate, DateTimePatternEnum.YYYY_MM_DD.getPattern());
        List<StatisticsDataVO> statisticsDataVOList = new ArrayList<>();

        for (StatisticsDataTypeEnum dataType : StatisticsDataTypeEnum.values()) {
            StatisticsDataVO statisticsDataVO = new StatisticsDataVO();
            statisticsDataVOList.add(statisticsDataVO);

            statisticsDataVO.setDataType(dataType.getType());
            for (String date : dateList) {
                StatisticsInfo statisticsInfo = statisticsInfoMap.get(date + dataType.getType());
                statisticsDataVO.getDateList().add(date);
                if (statisticsInfo == null) {
                    statisticsDataVO.getDataList().add(BigDecimal.ZERO);
                } else {
                    statisticsDataVO.getDataList().add(statisticsInfo.getDataValue());
                }
            }
        }
        return statisticsDataVOList;
    }

    @Override
    public void statisticsData(String date) {

        List<StatisticsInfo> statisticsInfoList = new ArrayList<>();
        //璁㈠崟閲戦
        BigDecimal yesterdayOrderAmount = orderInfoService.getOrderTotalAmount(date, new Integer[]{OrderStatusEnum.PAID.getStatus(),
                OrderStatusEnum.SHIPPED.getStatus(), OrderStatusEnum.COMPLETED.getStatus()});
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.setStatisticsDate(date);
        statisticsInfo.setDataType(StatisticsDataTypeEnum.SALE_AMOUNT.getType());
        statisticsInfo.setDataValue(yesterdayOrderAmount);
        statisticsInfoList.add(statisticsInfo);

        //閫€娆?
        BigDecimal yesterdayRefundAmount = orderInfoService.getOrderTotalAmount(date, new Integer[]{OrderStatusEnum.REFUNDED.getStatus()});
        statisticsInfo = new StatisticsInfo();
        statisticsInfo.setStatisticsDate(date);
        statisticsInfo.setDataType(StatisticsDataTypeEnum.REFUND_AMOUNT.getType());
        statisticsInfo.setDataValue(yesterdayRefundAmount);
        statisticsInfoList.add(statisticsInfo);

        //璁㈠崟鏁伴噺
        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setOrderTime(date);
        orderInfoQuery.setOrderStatusList(new Integer[]{OrderStatusEnum.PAID.getStatus(),
                OrderStatusEnum.SHIPPED.getStatus(), OrderStatusEnum.COMPLETED.getStatus()});
        Integer yesterdayOrderCount = orderInfoService.findCountByParam(orderInfoQuery);
        statisticsInfo = new StatisticsInfo();
        statisticsInfo.setStatisticsDate(date);
        statisticsInfo.setDataType(StatisticsDataTypeEnum.SALE_COUNT.getType());
        statisticsInfo.setDataValue(new BigDecimal(yesterdayOrderCount));
        statisticsInfoList.add(statisticsInfo);

        orderInfoQuery.setOrderStatusList(new Integer[]{OrderStatusEnum.REFUNDED.getStatus()});
        Integer yesterdayRefundOrderCount = orderInfoService.findCountByParam(orderInfoQuery);
        statisticsInfo = new StatisticsInfo();
        statisticsInfo.setStatisticsDate(date);
        statisticsInfo.setDataType(StatisticsDataTypeEnum.REFUND_COUNT.getType());
        statisticsInfo.setDataValue(new BigDecimal(yesterdayRefundOrderCount));
        statisticsInfoList.add(statisticsInfo);

        statisticsInfoMapper.insertOrUpdateBatch(statisticsInfoList);

    }
}
