package com.leaf.eexamen.enums;

import com.leaf.eexamen.utility.CommonConstant;

public enum ExamStatusEnum {

    PENDING(CommonConstant.EXAM_STATUS_PENDING),
    CLOSED(CommonConstant.EXAM_STATUS_CLOSED),
    START(CommonConstant.EXAM_STATUS_START);


    private String code;

    ExamStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ExamStatusEnum getEnum(String code){
        switch (code){
            case CommonConstant.EXAM_STATUS_START:
                return START;
            case CommonConstant.EXAM_STATUS_CLOSED:
                return CLOSED;
            default:
                return PENDING;
        }
    }
}
