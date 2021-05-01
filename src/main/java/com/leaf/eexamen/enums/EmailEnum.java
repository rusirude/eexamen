package com.leaf.eexamen.enums;

public enum EmailEnum {

    EFR("EFR"),
    EFSR("EFSR"),
    EFPR("EFPR");

    private String code;

    EmailEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
