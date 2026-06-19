package com.aimall.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum CommentStatusEnum {

    NORMAL(0, "姝ｅ父"),
    DEL(1, "宸插垹闄?);

    private Integer status;
    private String desc;

    CommentStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


    public static CommentStatusEnum getByStatus(Integer status) {
        Optional<CommentStatusEnum> typeEnum = Arrays.stream(CommentStatusEnum.values()).filter(value -> value.getStatus().equals(status)).findFirst();
        return typeEnum == null ? null : typeEnum.get();
    }
}

