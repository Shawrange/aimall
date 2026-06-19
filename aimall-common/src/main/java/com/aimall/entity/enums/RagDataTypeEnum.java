package com.aimall.entity.enums;


import java.util.Arrays;
import java.util.Optional;

public enum RagDataTypeEnum {
    PRODUCT("product", "鍟嗗搧鏁版嵁"),
    FAQ("faq", "FAQ鏁版嵁");

    private String type;

    private String desc;

    RagDataTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static RagDataTypeEnum getByType(String type) {
        Optional<RagDataTypeEnum> result = Arrays.stream(RagDataTypeEnum.values()).filter(value -> value.getType().equals(type)).findFirst();
        return result == null ? null : result.get();
    }
}

