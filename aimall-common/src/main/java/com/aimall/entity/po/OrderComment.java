package com.aimall.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *
 */
public class OrderComment implements Serializable {


    /**
     * 璁㈠崟ID
     */
    private String orderId;

    /**
     * 鍟嗗搧ID
     */
    private String productId;

    /**
     * 璇勪环鍐呭
     */
    private String commentContent;

    /**
     * 璇勪环鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentTime;

    /**
     * 璇勪环鍥剧墖
     */
    private String commentImages;

    /**
     * 璇勪环鏄熺骇
     */
    private Integer star;

    /**
     * 鍟嗗鍥炲
     */
    private String commentBizReply;

    /**
     * 杩借瘎
     */
    private String recommentContent;

    /**
     * 杩借瘎鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recommentTime;

    /**
     * 杩借瘎鍥剧墖
     */
    private String recommentImages;

    /**
     * 鐢ㄦ埛ID
     */
    private String userId;

    /**
     * 灞炴€т俊鎭?
     */
    private String propertyInfo;

    /**
     * 0:姝ｅ父 1:宸插垹闄?
     */
    private Integer status;

    private String nickName;

    private String avatar;

    private String productName;

    private String cover;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentContent() {
        return this.commentContent;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Date getCommentTime() {
        return this.commentTime;
    }

    public void setCommentImages(String commentImages) {
        this.commentImages = commentImages;
    }

    public String getCommentImages() {
        return this.commentImages;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getStar() {
        return this.star;
    }

    public void setCommentBizReply(String commentBizReply) {
        this.commentBizReply = commentBizReply;
    }

    public String getCommentBizReply() {
        return this.commentBizReply;
    }

    public void setRecommentContent(String recommentContent) {
        this.recommentContent = recommentContent;
    }

    public String getRecommentContent() {
        return this.recommentContent;
    }

    public void setRecommentTime(Date recommentTime) {
        this.recommentTime = recommentTime;
    }

    public Date getRecommentTime() {
        return this.recommentTime;
    }

    public void setRecommentImages(String recommentImages) {
        this.recommentImages = recommentImages;
    }

    public String getRecommentImages() {
        return this.recommentImages;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setPropertyInfo(String propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

    public String getPropertyInfo() {
        return this.propertyInfo;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "璁㈠崟ID:" + (orderId == null ? "绌? : orderId) + "锛屽晢鍝両D:" + (productId == null ? "绌? : productId) + "锛岃瘎浠峰唴瀹?" + (commentContent == null ? "绌? :
                commentContent) + "锛岃瘎浠锋椂闂?" + (commentTime == null ? "绌? : DateUtil.format(commentTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) +
                "锛岃瘎浠峰浘鐗?" + (commentImages == null ? "绌? : commentImages) + "锛岃瘎浠锋槦绾?" + (star == null ? "绌? : star) + "锛屽晢瀹跺洖澶?" + (commentBizReply == null ? "绌? :
                commentBizReply) + "锛岃拷璇?" + (recommentContent == null ? "绌? : recommentContent) + "锛岃拷璇勬椂闂?" + (recommentTime == null ? "绌? :
                DateUtil.format(recommentTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "锛岃拷璇勫浘鐗?" + (recommentImages == null ? "绌? : recommentImages) +
                "锛岀敤鎴稩D:" + (userId == null ? "绌? : userId) + "锛屽睘鎬т俊鎭?" + (propertyInfo == null ? "绌? : propertyInfo) + "锛?:姝ｅ父 1:宸插垹闄?" + (status == null ? "绌? : status);
    }
}

