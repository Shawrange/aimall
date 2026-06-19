package com.aimall.entity.po;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 鏁版嵁缁熻缁撴灉
 */
public class StatisticsInfo implements Serializable {


	/**
	 * 鏃ユ湡
	 */
	private String statisticsDate;

	/**
	 * 鏁版嵁绫诲瀷
	 */
	private Integer dataType;

	/**
	 * 缁熻鏁版嵁
	 */
	private BigDecimal dataValue;


	public void setStatisticsDate(String statisticsDate){
		this.statisticsDate = statisticsDate;
	}

	public String getStatisticsDate(){
		return this.statisticsDate;
	}

	public void setDataType(Integer dataType){
		this.dataType = dataType;
	}

	public Integer getDataType(){
		return this.dataType;
	}

	public void setDataValue(BigDecimal dataValue){
		this.dataValue = dataValue;
	}

	public BigDecimal getDataValue(){
		return this.dataValue;
	}

	@Override
	public String toString (){
		return "鏃ユ湡:"+(statisticsDate == null ? "绌? : statisticsDate)+"锛屾暟鎹被鍨?"+(dataType == null ? "绌? : dataType)+"锛岀粺璁℃暟鎹?"+(dataValue == null ? "绌? : dataValue);
	}
}

