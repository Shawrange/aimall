package com.aimall.entity.enums;


public enum UserSexEnum {

    WOMAN(0, "濂?),
    MAN(1, "鐢?),
    SECRECY(2, "淇濆瘑");;


    private Integer type;
    private String desc;

    UserSexEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static UserSexEnum getByType(Integer type) {
        for (UserSexEnum item : UserSexEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}

