package com.leaf.eexamen.enums;

import com.leaf.eexamen.utility.CommonConstant;

public enum DefaultStatusEnum {
	
    ACTIVE(CommonConstant.DEFAULT_STATUS_ACTIVE),
    INACTIVE(CommonConstant.DEFAULT_STATUS_INACTIVE);


    private String code;

    DefaultStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DefaultStatusEnum getEnum(String code){
        switch (code){
            case CommonConstant.DEFAULT_STATUS_ACTIVE:
                return ACTIVE;
            default:
                return INACTIVE;
        }
    }
}
