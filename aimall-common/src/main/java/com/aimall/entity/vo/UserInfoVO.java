package com.aimall.entity.vo;

import java.io.Serializable;


/**
 *
 */
public class UserInfoVO implements Serializable {

    /**
     * 鐢ㄦ埛ID
     */
    private String userId;

    /**
     * 閭
     */
    private String email;

    /**
     * 鏄电О
     */
    private String nickName;

    /**
     * 鐢ㄦ埛澶村儚
     */
    private String avatar;


    private Integer sex;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}

