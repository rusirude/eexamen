package com.leaf.eexamen.enums;

public enum ExamCategoryEnum {

    FEXAM("FEXAM","First Exam"),
    AUXEXAM("AUXEXAM","Actualisation");


    private String code;
    private String  description;

    ExamCategoryEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ExamCategoryEnum getEnum(String code){
        switch (code){
            case "FEXAM":
                return FEXAM;
            default:
                return AUXEXAM;
        }
    }
}
