package com.aimall.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum OrderItemStatusEnum {
    REFUND(0, "宸查€€娆?),
    NORMAL(1, "姝ｅ父");

    private Integer status;
    private String desc;

    OrderItemStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


    public static OrderItemStatusEnum getByStatus(Integer status) {
        Optional<OrderItemStatusEnum> typeEnum = Arrays.stream(OrderItemStatusEnum.values()).filter(value -> value.getStatus().equals(status)).findFirst();
        return typeEnum == null||typeEnum.isEmpty() ? null : typeEnum.get();
    }
}

