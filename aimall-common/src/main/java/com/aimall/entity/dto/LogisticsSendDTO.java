package com.aimall.entity.dto;

public class LogisticsSendDTO {

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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }
}

