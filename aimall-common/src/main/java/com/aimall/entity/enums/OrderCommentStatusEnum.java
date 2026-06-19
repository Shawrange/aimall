package com.aimall.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum OrderCommentStatusEnum {

    NOT_EVALUATED(0, "鏈瘎浠?),
    EVALUATED(1, "宸茶瘎浠?),
    ADDITIONAL_EVALUATED(2, "宸茶拷璇?);

    private Integer status;
    private String desc;

    OrderCommentStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


    public static OrderCommentStatusEnum getByStatus(Integer status) {
        Optional<OrderCommentStatusEnum> typeEnum = Arrays.stream(OrderCommentStatusEnum.values()).filter(value -> value.getStatus().equals(status)).findFirst();
        return typeEnum == null ? null : typeEnum.get();
    }
}

