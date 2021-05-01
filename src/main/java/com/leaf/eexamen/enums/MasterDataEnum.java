package com.leaf.eexamen.enums;

public enum MasterDataEnum {
	DEFAULT_PASSWORD("DEFAULT_PASSWORD"),
	COMPANY_NAME("COMPANY_NAME"),
	STUDENT_ROLE("STUDENT_ROLE");

	private String code;
	
	MasterDataEnum(String code) {
        this.code = code;
    }
	
	public String getCode() {
		return this.code;
	}
	
    public static MasterDataEnum getEnum(String code){
        switch (code){
        	case "COMPANY_NAME":
        		return COMPANY_NAME;
			case "STUDENT_ROLE":
				return STUDENT_ROLE;
            default:
                return DEFAULT_PASSWORD;
        }
    }
	
}
