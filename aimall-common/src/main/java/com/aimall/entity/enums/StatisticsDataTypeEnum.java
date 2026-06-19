package com.aimall.entity.enums;


public enum StatisticsDataTypeEnum {
    SALE_AMOUNT(1, "閿€鍞噾棰?),
    SALE_COUNT(2, "璁㈠崟鏁伴噺"),
    REFUND_AMOUNT(3, "閫€娆鹃噾棰?),
    REFUND_COUNT(4, "閫€娆炬暟閲?),
    ;

    private Integer type;

    private String desc;

    StatisticsDataTypeEnum(Integer type, String desc) {
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

