package com.aimall.entity.enums;


public enum AgentMessageStatusEnum {
    CANCEL(0, "鍙栨秷"),
    NORMAL(1, "姝ｅ父"),
    COMPLETE(2, "瀹屾垚");

    private Integer status;

    private String desc;

    AgentMessageStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}

