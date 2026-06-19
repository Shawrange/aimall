package com.aimall.entity.po;

import com.aimall.entity.enums.LogisticsStatusEnum;

import java.io.Serializable;
import java.util.List;


/**
 * 鐗╂祦淇℃伅琛?
 */
public class OrderLogisticsInfo implements Serializable {


    /**
     * 璁㈠崟缂栧彿
     */
    private String orderId;

    /**
     * 鐢ㄦ埛ID
     */
    private String userId;

    /**
     * 鐗╂祦鍗曞彿
     */
    private String logisticsNo;

    /**
     * 鐗╂祦鍏徃
     */
    private String logisticsCompany;

    /**
     * 鍙戣揣浜哄鍚?
     */
    private String senderName;

    /**
     * 鍙戣揣浜虹數璇?
     */
    private String senderPhone;

    /**
     * 鍙戣揣鍦板潃
     */
    private String senderAddress;

    /**
     * 鏀朵欢浜哄鍚?
     */
    private String receiverName;

    /**
     * 鏀朵欢浜虹數璇?
     */
    private String receiverPhone;

    /**
     * 鏀朵欢鍦板潃
     */
    private String receiverAddress;

    /**
     * 鐗╂祦鐘舵€侊細0寰呭彂璐?1杩愯緭涓?2宸查€佽揪 3璁㈠崟鍙栨秷
     */
    private Integer logisticsStatus;

    private String logisticsStatusName;

    public String getLogisticsStatusName() {
        LogisticsStatusEnum logisticsStatus = LogisticsStatusEnum.getByStatus(this.logisticsStatus);
        return logisticsStatus == null ? null : logisticsStatus.getDesc();
    }

    private List<OrderLogisticsInfoRecord> recordList;

    public List<OrderLogisticsInfoRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<OrderLogisticsInfoRecord> recordList) {
        this.recordList = recordList;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsNo() {
        return this.logisticsNo;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsCompany() {
        return this.logisticsCompany;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderPhone() {
        return this.senderPhone;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderAddress() {
        return this.senderAddress;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverName() {
        return this.receiverName;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPhone() {
        return this.receiverPhone;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    public void setLogisticsStatus(Integer logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public Integer getLogisticsStatus() {
        return this.logisticsStatus;
    }

    @Override
    public String toString() {
        return "璁㈠崟缂栧彿:" + (orderId == null ? "绌? : orderId) + "锛岀敤鎴稩D:" + (userId == null ? "绌? : userId) + "锛岀墿娴佸崟鍙?" + (logisticsNo == null ? "绌? : logisticsNo) +
                "锛岀墿娴佸叕鍙?" + (logisticsCompany == null ? "绌? : logisticsCompany) + "锛屽彂璐т汉濮撳悕:" + (senderName == null ? "绌? : senderName) + "锛屽彂璐т汉鐢佃瘽:" + (senderPhone == null ? "绌? : senderPhone) + "锛屽彂璐у湴鍧€:" + (senderAddress == null ? "绌? : senderAddress) + "锛屾敹浠朵汉濮撳悕:" + (receiverName == null ? "绌? : receiverName) + "锛屾敹浠朵汉鐢佃瘽:" + (receiverPhone == null ? "绌? : receiverPhone) + "锛屾敹浠跺湴鍧€:" + (receiverAddress == null ? "绌? : receiverAddress) + "锛岀墿娴佺姸鎬侊細0寰呭彂璐?1杩愯緭涓?2宸查€佽揪 3璁㈠崟鍙栨秷:" + (logisticsStatus == null ? "绌? : logisticsStatus);
    }
}

