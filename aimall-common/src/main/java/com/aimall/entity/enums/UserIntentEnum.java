package com.aimall.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum UserIntentEnum {
    PRODUCT_SEARCH("product_search", "鎼滅储鍟嗗搧"),
    QUERY_ORDER("query_order", "璁㈠崟鏌ヨ"),
    CHAT("chat", "鏅€氳亰澶?);

    UserIntentEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private String key;
    private String desc;

    public static UserIntentEnum getByCode(String code) {
        Optional<UserIntentEnum> typeEnum = Arrays.stream(UserIntentEnum.values()).filter(value -> value.toString().equals(code)).findFirst();
        return typeEnum == null || typeEnum.isEmpty() ? null : typeEnum.get();
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}

