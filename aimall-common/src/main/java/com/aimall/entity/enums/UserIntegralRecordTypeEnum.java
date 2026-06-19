package com.aimall.entity.enums;


import java.util.Arrays;
import java.util.Optional;

public enum UserIntegralRecordTypeEnum {

    CREATE_MUSIC_BACK(0, "鍒涗綔闊充箰澶辫触閫€鍥?),
    CREATE_MUSIC(1, "鍒涗綔闊充箰"),
    RECHARGE(2, "鍏呭€?),
    ADMIN_ADD(3, "绠＄悊鍛樿禒閫?),
    ADMIN_DEDUCT(4, "绠＄悊鍛樻墸鍑?);


    private Integer type;
    private String desc;

    UserIntegralRecordTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static UserIntegralRecordTypeEnum getByType(Integer type) {
        Optional<UserIntegralRecordTypeEnum> recordTypeEnum =
                Arrays.stream(UserIntegralRecordTypeEnum.values())
                        .filter(value -> value.getType().equals(type)).findFirst();
        return recordTypeEnum == null ||recordTypeEnum.isEmpty() ? null : recordTypeEnum.get();
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

