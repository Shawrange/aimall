package com.aimall.entity.po;

import java.util.Date;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 鐢ㄦ埛淇℃伅
 */
public class UserInfo implements Serializable {


	/**
	 * 鐢ㄦ埛id
	 */
	private String userId;

	/**
	 * 鏄电О
	 */
	private String nickName;

	/**
	 * 澶村儚
	 */
	private String avatar;

	/**
	 * 閭
	 */
	private String email;

	/**
	 * 瀵嗙爜
	 */
	private String password;

	/**
	 * 0:濂?1:鐢?2:鏈煡
	 */
	private Integer sex;

	/**
	 * 鍔犲叆鏃堕棿
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;

	/**
	 * 鏈€鍚庣櫥褰曟椂闂?
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	/**
	 * 鏈€鍚庣櫥褰旾P
	 */
	private String lastLoginIp;

	/**
	 * 0:绂佺敤 1:姝ｅ父
	 */
	private Integer status;

	/**
	 * 搴旂敤key
	 */
	private String apiKey;


	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return this.nickName;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return this.avatar;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setSex(Integer sex){
		this.sex = sex;
	}

	public Integer getSex(){
		return this.sex;
	}

	public void setJoinTime(Date joinTime){
		this.joinTime = joinTime;
	}

	public Date getJoinTime(){
		return this.joinTime;
	}

	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setLastLoginIp(String lastLoginIp){
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIp(){
		return this.lastLoginIp;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setApiKey(String apiKey){
		this.apiKey = apiKey;
	}

	public String getApiKey(){
		return this.apiKey;
	}

	@Override
	public String toString (){
		return "userId:" + (userId == null ? "" : userId)
				+ ", nickName:" + (nickName == null ? "" : nickName)
				+ ", avatar:" + (avatar == null ? "" : avatar)
				+ ", email:" + (email == null ? "" : email)
				+ ", sex:" + (sex == null ? "" : sex)
				+ ", joinTime:" + (joinTime == null ? "" : DateUtil.format(joinTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))
				+ ", lastLoginTime:" + (lastLoginTime == null ? "" : DateUtil.format(lastLoginTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))
				+ ", lastLoginIp:" + (lastLoginIp == null ? "" : lastLoginIp)
				+ ", status:" + (status == null ? "" : status);
	}
}

