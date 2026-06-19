package com.aimall.entity.query;

import java.math.BigDecimal;
import java.util.List;


/**
 * 璁㈠崟淇℃伅鍙傛暟
 */
public class OrderInfoQuery extends BaseParam {


    /**
     * 璁㈠崟ID
     */
    private String orderId;

    private String orderIdFuzzy;

    /**
     * 閲戦
     */
    private BigDecimal amount;

    /**
     * 鐢ㄦ埛ID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 璁㈠崟鍒涘缓鏃堕棿
     */
    private String orderTime;

    private String orderTimeStart;

    private String orderTimeEnd;

    /**
     * -1宸插垹闄?0:寰呬粯娆?1:宸蹭粯娆?寰呭彂璐? 2:宸插彂璐? 3:宸插畬鎴?4:宸插彇娑?5:宸插叧闂?6:宸查€€娆?7:閮ㄥ垎閫€娆?
     */
    private Integer orderStatus;

    /**
     * 鏀粯閫氶亾
     */
    private String payChannel;

    private String payChannelFuzzy;

    /**
     * 鏀粯鍦烘櫙
     */
    private String payScene;

    private String paySceneFuzzy;

    /**
     * 鏀粯璁㈠崟鍙?
     */
    private String payOrderId;

    private String payOrderIdFuzzy;

    /**
     * 閫氶亾ID
     */
    private String channelOrderId;

    private String channelOrderIdFuzzy;

    /**
     * 璇勪环鐘舵€?0:鏈瘎浠? 1:宸茶瘎浠? 2:宸茶拷璇?
     */
    private Integer commentStatus;


    private Boolean queryItems;

    private List<String> orderIdList;

    private Integer[] orderStatusList;

    private Integer[] executeOrderStatusList;

    private Boolean queryUser;

    private String productNameFuzzy;


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

    public void setUserIdFuzzy(String userIdFuzzy) {
        this.userIdFuzzy = userIdFuzzy;
    }

    public String getUserIdFuzzy() {
        return this.userIdFuzzy;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTimeStart(String orderTimeStart) {
        this.orderTimeStart = orderTimeStart;
    }

    public String getOrderTimeStart() {
        return this.orderTimeStart;
    }

    public void setOrderTimeEnd(String orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    public String getOrderTimeEnd() {
        return this.orderTimeEnd;
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

    public void setPayChannelFuzzy(String payChannelFuzzy) {
        this.payChannelFuzzy = payChannelFuzzy;
    }

    public String getPayChannelFuzzy() {
        return this.payChannelFuzzy;
    }

    public void setPayScene(String payScene) {
        this.payScene = payScene;
    }

    public String getPayScene() {
        return this.payScene;
    }

    public void setPaySceneFuzzy(String paySceneFuzzy) {
        this.paySceneFuzzy = paySceneFuzzy;
    }

    public String getPaySceneFuzzy() {
        return this.paySceneFuzzy;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getPayOrderId() {
        return this.payOrderId;
    }

    public void setPayOrderIdFuzzy(String payOrderIdFuzzy) {
        this.payOrderIdFuzzy = payOrderIdFuzzy;
    }

    public String getPayOrderIdFuzzy() {
        return this.payOrderIdFuzzy;
    }

    public void setChannelOrderId(String channelOrderId) {
        this.channelOrderId = channelOrderId;
    }

    public String getChannelOrderId() {
        return this.channelOrderId;
    }

    public void setChannelOrderIdFuzzy(String channelOrderIdFuzzy) {
        this.channelOrderIdFuzzy = channelOrderIdFuzzy;
    }

    public String getChannelOrderIdFuzzy() {
        return this.channelOrderIdFuzzy;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getCommentStatus() {
        return this.commentStatus;
    }

    public Boolean getQueryItems() {
        return queryItems;
    }

    public void setQueryItems(Boolean queryItems) {
        this.queryItems = queryItems;
    }

    public List<String> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<String> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public Integer[] getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(Integer[] orderStatusList) {
        this.orderStatusList = orderStatusList;
    }

    public Integer[] getExecuteOrderStatusList() {
        return executeOrderStatusList;
    }

    public void setExecuteOrderStatusList(Integer[] executeOrderStatusList) {
        this.executeOrderStatusList = executeOrderStatusList;
    }

    public Boolean getQueryUser() {
        return queryUser;
    }

    public void setQueryUser(Boolean queryUser) {
        this.queryUser = queryUser;
    }

    public String getProductNameFuzzy() {
        return productNameFuzzy;
    }

    public void setProductNameFuzzy(String productNameFuzzy) {
        this.productNameFuzzy = productNameFuzzy;
    }
}

