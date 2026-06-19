package com.aimall.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 璐墿杞?
 */
public class ProductCart implements Serializable {


	/**
	 * 璐墿杞D
	 */
	private String cartId;

	/**
	 * 鐢ㄦ埛ID
	 */
	private String userId;

	/**
	 * 鍟嗗搧ID
	 */
	private String productId;

	/**
	 * 灞炴€у€糹d缁?
	 */
	private String propertyValueIds;

	/**
	 * 灞炴€у€糹d缁刪ash
	 */
	private String propertyValueIdHash;

	/**
	 * 鏁伴噺
	 */
	private Integer buyCount;

	/**
	 * 鏇存柊鏃堕棿
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

	/**
	 * 鍒涘缓鏃堕棿
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public void setCartId(String cartId){
		this.cartId = cartId;
	}

	public String getCartId(){
		return this.cartId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return this.productId;
	}

	public void setPropertyValueIds(String propertyValueIds){
		this.propertyValueIds = propertyValueIds;
	}

	public String getPropertyValueIds(){
		return this.propertyValueIds;
	}

	public void setPropertyValueIdHash(String propertyValueIdHash){
		this.propertyValueIdHash = propertyValueIdHash;
	}

	public String getPropertyValueIdHash(){
		return this.propertyValueIdHash;
	}

	public void setBuyCount(Integer buyCount){
		this.buyCount = buyCount;
	}

	public Integer getBuyCount(){
		return this.buyCount;
	}

	public void setLastUpdateTime(Date lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getLastUpdateTime(){
		return this.lastUpdateTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	@Override
	public String toString (){
		return "璐墿杞D:"+(cartId == null ? "绌? : cartId)+"锛岀敤鎴稩D:"+(userId == null ? "绌? : userId)+"锛屽晢鍝両D:"+(productId == null ? "绌? : productId)+"锛屽睘鎬у€糹d缁?"+(propertyValueIds == null ? "绌? : propertyValueIds)+"锛屽睘鎬у€糹d缁刪ash:"+(propertyValueIdHash == null ? "绌? : propertyValueIdHash)+"锛屾暟閲?"+(buyCount == null ? "绌? : buyCount)+"锛屾洿鏂版椂闂?"+(lastUpdateTime == null ? "绌? : DateUtil.format(lastUpdateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"锛屽垱寤烘椂闂?"+(createTime == null ? "绌? : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}

