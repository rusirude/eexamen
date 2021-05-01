package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;

public class SysRoleDTO extends CommonDTO{
	
	private String code;
	private String description;
	private String statusCode;
	private String statusDescription;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRoleDTO [code=");
		builder.append(code);
		builder.append(", description=");
		builder.append(description);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", statusDescription=");
		builder.append(statusDescription);
		builder.append("]");
		return builder.toString();
	}
	
	
}
