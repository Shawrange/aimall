package com.aimall.entity.po;

import java.util.Date;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 
 */
public class OrderLogisticsInfoRecord implements Serializable {


	/**
	 * 璁板綍ID
	 */
	private Integer recordId;

	/**
	 * 璁㈠崟ID
	 */
	private String orderId;

	/**
	 * 璁板綍鏃堕棿
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date recordTime;

	/**
	 * 璁板綍鍦板潃
	 */
	private String recordAddress;


	public void setRecordId(Integer recordId){
		this.recordId = recordId;
	}

	public Integer getRecordId(){
		return this.recordId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

	public void setRecordTime(Date recordTime){
		this.recordTime = recordTime;
	}

	public Date getRecordTime(){
		return this.recordTime;
	}

	public void setRecordAddress(String recordAddress){
		this.recordAddress = recordAddress;
	}

	public String getRecordAddress(){
		return this.recordAddress;
	}

	@Override
	public String toString (){
		return "璁板綍ID:"+(recordId == null ? "绌? : recordId)+"锛岃鍗旾D:"+(orderId == null ? "绌? : orderId)+"锛岃褰曟椂闂?"+(recordTime == null ? "绌? : DateUtil.format(recordTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"锛岃褰曞湴鍧€:"+(recordAddress == null ? "绌? : recordAddress);
	}
}

