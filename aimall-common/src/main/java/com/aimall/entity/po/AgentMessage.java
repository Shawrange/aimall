package com.aimall.entity.po;

import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 */
public class AgentMessage implements Serializable {


	/**
	 * 娑堟伅ID
	 */
	private Integer messageId;

	/**
	 * AI娑堟伅
	 */
	private String assistantMessage;

	/**
	 * 鐢ㄦ埛娑堟伅
	 */
	private String userMessage;

	/**
	 * 鍙戦€佹椂闂?
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;

	/**
	 * 鐢ㄦ埛ID
	 */
	private String userId;

	/**
	 * 0:鐢ㄦ埛鍙栨秷 1:鍥炵瓟涓?2:瀹屾垚
	 */
	private Integer status;

	/**
	 * 涓氬姟绫诲瀷
	 */
	private String bizType;

	/**
	 * 涓氬姟鏁版嵁
	 */
	private String bizData;


	public void setMessageId(Integer messageId){
		this.messageId = messageId;
	}

	public Integer getMessageId(){
		return this.messageId;
	}

	public void setAssistantMessage(String assistantMessage){
		this.assistantMessage = assistantMessage;
	}

	public String getAssistantMessage(){
		return this.assistantMessage;
	}

	public void setUserMessage(String userMessage){
		this.userMessage = userMessage;
	}

	public String getUserMessage(){
		return this.userMessage;
	}

	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}

	public Date getSendTime(){
		return this.sendTime;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setBizType(String bizType){
		this.bizType = bizType;
	}

	public String getBizType(){
		return this.bizType;
	}

	public void setBizData(String bizData){
		this.bizData = bizData;
	}

	public String getBizData(){
		return this.bizData;
	}

	@Override
	public String toString (){
		return "娑堟伅ID:"+(messageId == null ? "绌? : messageId)+"锛孉I娑堟伅:"+(assistantMessage == null ? "绌? : assistantMessage)+"锛岀敤鎴锋秷鎭?"+(userMessage == null ? "绌? : userMessage)+"锛屽彂閫佹椂闂?"+(sendTime == null ? "绌? : DateUtil.format(sendTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"锛岀敤鎴稩D:"+(userId == null ? "绌? : userId)+"锛?:鐢ㄦ埛鍙栨秷 1:鍥炵瓟涓?2:瀹屾垚:"+(status == null ? "绌? : status)+"锛屼笟鍔＄被鍨?"+(bizType == null ? "绌? : bizType)+"锛屼笟鍔℃暟鎹?"+(bizData == null ? "绌? : bizData);
	}
}

