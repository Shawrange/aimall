package com.aimall.entity.enums;

public enum MessageOutPutTypeEnum {
    OUTPUTTING(0, "杈撳嚭涓?), DONE(1, "澶勭悊瀹屾垚"), ERROR(2, "杈撳嚭澶辫触");
    private Integer type;
    private String desc;

    MessageOutPutTypeEnum(Integer type, String desc) {
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

