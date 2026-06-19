package com.aimall.entity.enums;

public enum MessageTypeEnum {
    USER(0, "鐢ㄦ埛鍙戦€?), ASSISTANT(1, "AI鍥炵瓟");
    private Integer type;
    private String desc;

    MessageTypeEnum(Integer type, String desc) {
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

