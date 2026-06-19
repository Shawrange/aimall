package com.aimall.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.entity.enums.OrderStatusEnum;
import com.aimall.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 璁㈠崟淇℃伅
 */
public class OrderInfo implements Serializable {


    /**
     * 璁㈠崟ID
     */
    private String orderId;

    /**
     * 閲戦
     */
    private BigDecimal amount;

    /**
     * 鐢ㄦ埛ID
     */
    private String userId;

    /**
     * 璁㈠崟鍒涘缓鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    /**
     * -1宸插垹闄?0:寰呬粯娆?1:宸蹭粯娆?寰呭彂璐? 2:宸插彂璐? 3:宸插畬鎴?4:宸插彇娑?5:宸插叧闂?6:宸查€€娆?7:閮ㄥ垎閫€娆?
     */
    private Integer orderStatus;

    /**
     * 鏀粯閫氶亾
     */
    private String payChannel;

    /**
     * 鏀粯鍦烘櫙
     */
    private String payScene;

    /**
     * 鏀粯璁㈠崟鍙?
     */
    private String payOrderId;

    /**
     * 閫氶亾ID
     */
    private String channelOrderId;

    /**
     * 璇勪环鐘舵€?0:鏈瘎浠? 1:宸茶瘎浠? 2:宸茶拷璇?
     */
    private Integer commentStatus;


    private List<OrderItem> orderItemList;

    private String orderStatusName;

    private String nickName;

    private String avatar;

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

    public String getOrderStatusName() {
        OrderStatusEnum orderStatusEnum = OrderStatusEnum.getByStatus(orderStatus);
        return orderStatusEnum == null ? null : orderStatusEnum.getDesc();
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() {
        return this.orderTime;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() {
        return this.orderStatus;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannel() {
        return this.payChannel;
    }

    public void setPayScene(String payScene) {
        this.payScene = payScene;
    }

    public String getPayScene() {
        return this.payScene;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getPayOrderId() {
        return this.payOrderId;
    }

    public void setChannelOrderId(String channelOrderId) {
        this.channelOrderId = channelOrderId;
    }

    public String getChannelOrderId() {
        return this.channelOrderId;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getCommentStatus() {
        return this.commentStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    @Override
    public String toString() {
        return "璁㈠崟ID:" + (orderId == null ? "绌? : orderId) + "锛岄噾棰?" + (amount == null ? "绌? : amount) + "锛岀敤鎴稩D:" + (userId == null ? "绌? : userId) + "锛岃鍗曞垱寤烘椂闂?" + (orderTime == null ? "绌? : DateUtil.format(orderTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "锛?1宸插垹闄?0:寰呬粯娆?1:宸蹭粯娆?寰呭彂璐? 2:宸插彂璐? 3:宸插畬鎴?4:宸插彇娑?5:宸插叧闂?6:宸查€€娆?7:閮ㄥ垎閫€娆?" + (orderStatus == null ? "绌? : orderStatus) + "锛屾敮浠橀€氶亾:" + (payChannel == null ? "绌? : payChannel) + "锛屾敮浠樺満鏅?" + (payScene == null ? "绌? : payScene) + "锛屾敮浠樿鍗曞彿:" + (payOrderId == null ? "绌? : payOrderId) + "锛岄€氶亾ID:" + (channelOrderId == null ? "绌? : channelOrderId) + "锛岃瘎浠风姸鎬?0:鏈瘎浠? 1:宸茶瘎浠? 2:宸茶拷璇?" + (commentStatus == null ? "绌? : commentStatus);
    }
}

