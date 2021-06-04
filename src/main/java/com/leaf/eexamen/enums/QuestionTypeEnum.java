package com.leaf.eexamen.enums;

public enum QuestionTypeEnum {

    THEORIM("THEORIM","THEORIM"),
    WETGEVING("WETGEVING","WETGEVING");


    private String code;
    private String  description;

    QuestionTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static QuestionTypeEnum getEnum(String code){
        switch (code){
            case "THEORIM":
                return THEORIM;
            default:
                return WETGEVING;
        }
    }
}
