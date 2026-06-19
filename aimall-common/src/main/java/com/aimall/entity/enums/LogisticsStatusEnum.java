package com.aimall.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum LogisticsStatusEnum {
    PENDING_SHIPMENT(0, "寰呭彂璐?),
    IN_TRANSIT(1, "杩愯緭涓?),
    DELIVERED(2, "宸查€佽揪"),
    CANCELLED(3, "璁㈠崟鍙栨秷");

    private Integer status;
    private String desc;

    LogisticsStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static LogisticsStatusEnum getByStatus(Integer status) {
        Optional<LogisticsStatusEnum> resultEnum = Arrays.stream(LogisticsStatusEnum.values()).filter(value -> value.getStatus().equals(status)).findFirst();
        return resultEnum == null || resultEnum.isEmpty() ? null : resultEnum.get();
    }
}
