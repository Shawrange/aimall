package com.aimall.entity.enums;


import java.util.Arrays;
import java.util.Optional;

public enum DefaultTypeEnum {

    NOT_DEFAULT(0, "闈為粯璁?),
    DEFAULT(1, "榛樿");


    private Integer type;
    private String desc;

    DefaultTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static DefaultTypeEnum getByStatus(Integer type) {
        Optional<DefaultTypeEnum> result =
                Arrays.stream(DefaultTypeEnum.values())
                        .filter(value -> value.getType().equals(type)).findFirst();
        return result == null ? null : result.get();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

