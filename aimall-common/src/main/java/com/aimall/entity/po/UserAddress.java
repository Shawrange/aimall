package com.aimall.entity.po;

import com.aimall.entity.valid.UpdateGroup;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;


/**
 *
 */
public class UserAddress implements Serializable {


    /**
     * 鍦板潃ID
     */
    @NotEmpty(groups = UpdateGroup.class)
    private String addressId;

    /**
     * 鐢ㄦ埛ID
     */
    private String userId;

    /**
     * 璇︾粏鍦板潃
     */
    @NotEmpty
    private String address;

    /**
     * 鏀惰揣浜?
     */
    @NotEmpty
    private String addressee;

    /**
     * 鎵嬫満鍙风爜
     */
    @NotEmpty
    private String phone;

    /**
     * 榛樿绫诲瀷0:闈為粯璁? 1:榛樿
     */
    private Integer defaultType;

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressId() {
        return this.addressId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getAddressee() {
        return this.addressee;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setDefaultType(Integer defaultType) {
        this.defaultType = defaultType;
    }

    public Integer getDefaultType() {
        return this.defaultType;
    }

    @Override
    public String toString() {
        return "鍦板潃ID:" + (addressId == null ? "绌? : addressId) + "锛岀敤鎴稩D:" + (userId == null ? "绌? : userId) + "锛岃缁嗗湴鍧€:" + (address == null ? "绌? : address) + "锛屾敹璐т汉:" + (addressee == null ? "绌? : addressee) + "锛屾墜鏈哄彿鐮?" + (phone == null ? "绌? : phone) + "锛岄粯璁ょ被鍨?:闈為粯璁? 1:榛樿:" + (defaultType == null ? "绌? : defaultType);
    }
}

