package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;

public class AuthorityDTO  extends CommonDTO {

	private String code;
	private String description;
	private String url;
	private String authCode;
	private String sectionCode;
	private String sectionDescription;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getSectionDescription() {
		return sectionDescription;
	}
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
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
	
}
