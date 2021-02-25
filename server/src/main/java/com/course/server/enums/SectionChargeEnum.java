package com.course.server.enums;

public enum SectionChargeEnum {
    FREE("F", "免费"),
    CHARGE("G", "收费"),
    ;

    private String code;
    private String desc;

    SectionChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
