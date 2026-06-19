package com.aimall.entity.query;

import java.math.BigDecimal;


/**
 * 鏁版嵁缁熻缁撴灉鍙傛暟
 */
public class StatisticsInfoQuery extends BaseParam {


    /**
     * 鏃ユ湡
     */
    private String statisticsDate;

    private String statisticsDateFuzzy;

    /**
     * 鏁版嵁绫诲瀷
     */
    private Integer dataType;

    /**
     * 缁熻鏁版嵁
     */
    private BigDecimal dataValue;

    private String statisticsDateStart;

    private String statisticsDateEnd;

    public String getStatisticsDateStart() {
        return statisticsDateStart;
    }

    public void setStatisticsDateStart(String statisticsDateStart) {
        this.statisticsDateStart = statisticsDateStart;
    }

    public String getStatisticsDateEnd() {
        return statisticsDateEnd;
    }

    public void setStatisticsDateEnd(String statisticsDateEnd) {
        this.statisticsDateEnd = statisticsDateEnd;
    }

    public void setStatisticsDate(String statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public String getStatisticsDate() {
        return this.statisticsDate;
    }

    public void setStatisticsDateFuzzy(String statisticsDateFuzzy) {
        this.statisticsDateFuzzy = statisticsDateFuzzy;
    }

    public String getStatisticsDateFuzzy() {
        return this.statisticsDateFuzzy;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    public BigDecimal getDataValue() {
        return this.dataValue;
    }

}

