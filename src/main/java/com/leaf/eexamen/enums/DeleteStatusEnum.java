package com.leaf.eexamen.enums;

import com.leaf.eexamen.utility.CommonConstant;

public enum DeleteStatusEnum {
	
    DELETE(CommonConstant.STATUS_CATEGORY_DELETE);


    private String code;

    DeleteStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DeleteStatusEnum getEnum(String code){
        switch (code){
            case CommonConstant.STATUS_CATEGORY_DELETE:
                return DELETE;
            default:
                return null;
        }
    }
}
