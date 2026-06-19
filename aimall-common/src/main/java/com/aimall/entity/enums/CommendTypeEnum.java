package com.aimall.entity.enums;


public enum CommendTypeEnum {
    NOT_COMMEND(0, "鏈帹鑽?), COMMEND(1, "宸叉帹鑽?);

    private Integer type;

    private String desc;

    CommendTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}

