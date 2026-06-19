package com.aimall.entity.po;

import com.aimall.entity.enums.OrderItemStatusEnum;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 璁㈠崟鏄庣粏琛?
 */
public class OrderItem implements Serializable {


    /**
     * 璁㈠崟鏄庣粏ID
     */
    private String orderItemId;

    /**
     * 璁㈠崟ID
     */
    private String orderId;

    /**
     * 灏侀潰
     */
    private String cover;

    /**
     * 鍟嗗搧ID
     */
    private String productId;

    /**
     * 鍟嗗搧鍚嶇О
     */
    private String productName;

    /**
     * 灞炴€у€糹d缁刪ash
     */
    private String propertyValueIdHash;

    /**
     * 灞炴€т俊鎭?
     */
    private String propertyInfo;

    /**
     * 浠锋牸
     */
    private BigDecimal itemAmount;

    /**
     * 鏁伴噺
     */
    private Integer buyCount;

    /**
     * 鐘舵€?1:姝ｅ父 0:宸查€€娆?
     */
    private Integer orderItemStatus;

    /**
     * 澶囨敞
     */
    private String remark;

    /**
     * 閫€娆捐鍗曞彿
     */
    private String refundOrderId;

    private String orderItemStatusName;

    public String getOrderItemStatusName() {
        OrderItemStatusEnum orderItemStatusEnum = OrderItemStatusEnum.getByStatus(orderItemStatus);
        return orderItemStatusEnum == null ? "" : orderItemStatusEnum.getDesc();
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderItemId() {
        return this.orderItemId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return this.cover;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setPropertyValueIdHash(String propertyValueIdHash) {
        this.propertyValueIdHash = propertyValueIdHash;
    }

    public String getPropertyValueIdHash() {
        return this.propertyValueIdHash;
    }

    public void setPropertyInfo(String propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

    public String getPropertyInfo() {
        return this.propertyInfo;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public BigDecimal getItemAmount() {
        return this.itemAmount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Integer getBuyCount() {
        return this.buyCount;
    }

    public void setOrderItemStatus(Integer orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public Integer getOrderItemStatus() {
        return this.orderItemStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRefundOrderId(String refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

    public String getRefundOrderId() {
        return this.refundOrderId;
    }

    @Override
    public String toString() {
        return "璁㈠崟鏄庣粏ID:" + (orderItemId == null ? "绌? : orderItemId) + "锛岃鍗旾D:" + (orderId == null ? "绌? : orderId) + "锛屽皝闈?" + (cover == null ? "绌? : cover) + "锛屽晢鍝両D:" + (productId == null ? "绌? : productId) + "锛屽晢鍝佸悕绉?" + (productName == null ? "绌? : productName) + "锛屽睘鎬у€糹d缁刪ash:" + (propertyValueIdHash == null ? "绌? : propertyValueIdHash) + "锛屽睘鎬т俊鎭?" + (propertyInfo == null ? "绌? : propertyInfo) + "锛屼环鏍?" + (itemAmount == null ? "绌? : itemAmount) + "锛屾暟閲?" + (buyCount == null ? "绌? : buyCount) + "锛岀姸鎬?1:姝ｅ父 0:宸查€€娆?" + (orderItemStatus == null ? "绌? : orderItemStatus) + "锛屽娉?" + (remark == null ? "绌? : remark) + "锛岄€€娆捐鍗曞彿:" + (refundOrderId == null ? "绌? : refundOrderId);
    }
}

