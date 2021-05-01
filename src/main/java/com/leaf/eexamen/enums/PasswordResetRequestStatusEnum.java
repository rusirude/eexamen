package com.leaf.eexamen.enums;

import com.leaf.eexamen.utility.CommonConstant;

public enum PasswordResetRequestStatusEnum {

    REQUEST(CommonConstant.PASSWORD_REST_REQUEST_STATUS_ACTIVE),
    PRESET(CommonConstant.PASSWORD_REST_REQUEST_STATUS_INACTIVE);


    private String code;

    PasswordResetRequestStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PasswordResetRequestStatusEnum getEnum(String code){
        switch (code){
            case CommonConstant.DEFAULT_STATUS_ACTIVE:
                return REQUEST;
            default:
                return PRESET;
        }
    }
}
