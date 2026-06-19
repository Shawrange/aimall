package com.aimall.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum OrderStatusEnum {
    DELETE(-1, "宸插垹闄?),
    WAIT_PAYMENT(0, "寰呬粯娆?),
    PAID(1, "宸蹭粯娆?寰呭彂璐?),
    SHIPPED(2, "宸插彂璐?),
    COMPLETED(3, "宸插畬鎴?),
    CANCELLED(4, "浜ゆ槗鍙栨秷"),
    CLOSED(5, "浜ゆ槗鍏抽棴"),
    REFUNDED(6, "宸查€€娆?浜ゆ槗鍏抽棴"),
    PARTIALLY_REFUNDED(7, "閮ㄥ垎閫€娆?);

    private Integer status;
    private String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


    public static OrderStatusEnum getByStatus(Integer status) {
        Optional<OrderStatusEnum> typeEnum = Arrays.stream(OrderStatusEnum.values()).filter(value -> value.getStatus().equals(status)).findFirst();
        return typeEnum == null ? null : typeEnum.get();
    }
}

