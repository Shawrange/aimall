package com.aimall.entity.enums;

import com.aimall.utils.StringTools;

import java.util.Arrays;
import java.util.Optional;

public enum SearchFieldTypeEnum {

    COMPOSITE("composite", "_score", "缁煎悎"),
    SALE("sale", "totalSale", "閿€閲?),
    PRICE("price", "minPrice", "浠锋牸");

    private String fieldType;
    private String field;
    private String desc;

    SearchFieldTypeEnum(String fieldType, String field, String desc) {
        this.fieldType = fieldType;
        this.field = field;
        this.desc = desc;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getDesc() {
        return desc;
    }

    public String getField() {
        return field;
    }

    public static SearchFieldTypeEnum getByFieldType(String fieldType) {
        if (StringTools.isEmpty(fieldType)) {
            return null;
        }
        Optional<SearchFieldTypeEnum> result = Arrays.stream(SearchFieldTypeEnum.values()).filter(value -> value.getFieldType().equals(fieldType)).findFirst();
        return result == null ? null : result.get();
    }
}

