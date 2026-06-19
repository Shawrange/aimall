package com.aimall.entity.query;

/**
 * 鍙傛暟
 */
public class OrderCommentQuery extends BaseParam {


    /**
     * 璁㈠崟ID
     */
    private String orderId;

    private String orderIdFuzzy;

    /**
     * 鍟嗗搧ID
     */
    private String productId;

    private String productIdFuzzy;

    /**
     * 璇勪环鍐呭
     */
    private String commentContent;

    private String commentContentFuzzy;

    /**
     * 璇勪环鏃堕棿
     */
    private String commentTime;

    private String commentTimeStart;

    private String commentTimeEnd;

    /**
     * 璇勪环鍥剧墖
     */
    private String commentImages;

    private String commentImagesFuzzy;

    /**
     * 璇勪环鏄熺骇
     */
    private Integer star;

    /**
     * 鍟嗗鍥炲
     */
    private String commentBizReply;

    private String commentBizReplyFuzzy;

    /**
     * 杩借瘎
     */
    private String recommentContent;

    private String recommentContentFuzzy;

    /**
     * 杩借瘎鏃堕棿
     */
    private String recommentTime;

    private String recommentTimeStart;

    private String recommentTimeEnd;

    /**
     * 杩借瘎鍥剧墖
     */
    private String recommentImages;

    private String recommentImagesFuzzy;

    /**
     * 鐢ㄦ埛ID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 灞炴€т俊鎭?
     */
    private String propertyInfo;

    /**
     * 0:姝ｅ父 1:宸插垹闄?
     */
    private Integer status;

    private String propertyInfoFuzzy;

    private Boolean queryUserInfo;

    private Boolean queryProduct;

    private String nickNameFuzzy;

    public void setNickNameFuzzy(String nickNameFuzzy) {
        this.nickNameFuzzy = nickNameFuzzy;
    }

    public void setProductNameFuzzy(String productNameFuzzy) {
        this.productNameFuzzy = productNameFuzzy;
    }

    private String productNameFuzzy;

    public String getNickNameFuzzy() {
        return nickNameFuzzy;
    }

    public String getProductNameFuzzy() {
        return productNameFuzzy;
    }

    public Boolean getQueryUserInfo() {
        return queryUserInfo;
    }

    public void setQueryUserInfo(Boolean queryUserInfo) {
        this.queryUserInfo = queryUserInfo;
    }

    public Boolean getQueryProduct() {
        return queryProduct;
    }

    public void setQueryProduct(Boolean queryProduct) {
        this.queryProduct = queryProduct;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderIdFuzzy(String orderIdFuzzy) {
        this.orderIdFuzzy = orderIdFuzzy;
    }

    public String getOrderIdFuzzy() {
        return this.orderIdFuzzy;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductIdFuzzy(String productIdFuzzy) {
        this.productIdFuzzy = productIdFuzzy;
    }

    public String getProductIdFuzzy() {
        return this.productIdFuzzy;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentContent() {
        return this.commentContent;
    }

    public void setCommentContentFuzzy(String commentContentFuzzy) {
        this.commentContentFuzzy = commentContentFuzzy;
    }

    public String getCommentContentFuzzy() {
        return this.commentContentFuzzy;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentTime() {
        return this.commentTime;
    }

    public void setCommentTimeStart(String commentTimeStart) {
        this.commentTimeStart = commentTimeStart;
    }

    public String getCommentTimeStart() {
        return this.commentTimeStart;
    }

    public void setCommentTimeEnd(String commentTimeEnd) {
        this.commentTimeEnd = commentTimeEnd;
    }

    public String getCommentTimeEnd() {
        return this.commentTimeEnd;
    }

    public void setCommentImages(String commentImages) {
        this.commentImages = commentImages;
    }

    public String getCommentImages() {
        return this.commentImages;
    }

    public void setCommentImagesFuzzy(String commentImagesFuzzy) {
        this.commentImagesFuzzy = commentImagesFuzzy;
    }

    public String getCommentImagesFuzzy() {
        return this.commentImagesFuzzy;
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

    public void setCommentBizReplyFuzzy(String commentBizReplyFuzzy) {
        this.commentBizReplyFuzzy = commentBizReplyFuzzy;
    }

    public String getCommentBizReplyFuzzy() {
        return this.commentBizReplyFuzzy;
    }

    public void setRecommentContent(String recommentContent) {
        this.recommentContent = recommentContent;
    }

    public String getRecommentContent() {
        return this.recommentContent;
    }

    public void setRecommentContentFuzzy(String recommentContentFuzzy) {
        this.recommentContentFuzzy = recommentContentFuzzy;
    }

    public String getRecommentContentFuzzy() {
        return this.recommentContentFuzzy;
    }

    public void setRecommentTime(String recommentTime) {
        this.recommentTime = recommentTime;
    }

    public String getRecommentTime() {
        return this.recommentTime;
    }

    public void setRecommentTimeStart(String recommentTimeStart) {
        this.recommentTimeStart = recommentTimeStart;
    }

    public String getRecommentTimeStart() {
        return this.recommentTimeStart;
    }

    public void setRecommentTimeEnd(String recommentTimeEnd) {
        this.recommentTimeEnd = recommentTimeEnd;
    }

    public String getRecommentTimeEnd() {
        return this.recommentTimeEnd;
    }

    public void setRecommentImages(String recommentImages) {
        this.recommentImages = recommentImages;
    }

    public String getRecommentImages() {
        return this.recommentImages;
    }

    public void setRecommentImagesFuzzy(String recommentImagesFuzzy) {
        this.recommentImagesFuzzy = recommentImagesFuzzy;
    }

    public String getRecommentImagesFuzzy() {
        return this.recommentImagesFuzzy;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserIdFuzzy(String userIdFuzzy) {
        this.userIdFuzzy = userIdFuzzy;
    }

    public String getUserIdFuzzy() {
        return this.userIdFuzzy;
    }

    public void setPropertyInfo(String propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

    public String getPropertyInfo() {
        return this.propertyInfo;
    }

    public void setPropertyInfoFuzzy(String propertyInfoFuzzy) {
        this.propertyInfoFuzzy = propertyInfoFuzzy;
    }

    public String getPropertyInfoFuzzy() {
        return this.propertyInfoFuzzy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

