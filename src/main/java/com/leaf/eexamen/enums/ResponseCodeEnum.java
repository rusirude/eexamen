package com.leaf.eexamen.enums;

import com.leaf.eexamen.utility.CommonConstant;

public enum ResponseCodeEnum {
	SUCCESS(CommonConstant.RESPONSE_SUCCESS),
	FAILED(CommonConstant.RESPONSE_FAILED);
	
	private String code;

	private ResponseCodeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
    public static ResponseCodeEnum getEnum(String code){
        switch (code){
            case CommonConstant.RESPONSE_SUCCESS:
                return SUCCESS;
            default:
                return FAILED;
        }
    }
	
	
}
