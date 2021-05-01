package com.leaf.eexamen.enums;

import com.leaf.eexamen.utility.CommonConstant;

public enum StatusCategoryEnum {
	
	DEFAULT(CommonConstant.STATUS_CATEGORY_DEFAULT),
	EXAM(CommonConstant.STATUS_CATEGORY_EXAM),
    DELETE(CommonConstant.STATUS_CATEGORY_DELETE);


    private String code;

    StatusCategoryEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static StatusCategoryEnum getEnum(String code){
        switch (code){
            case CommonConstant.STATUS_CATEGORY_DEFAULT:
                return DEFAULT;
            case CommonConstant.STATUS_CATEGORY_EXAM:
                return EXAM;
            default:
                return DELETE;
        }
}
}
