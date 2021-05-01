package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;

public class PasswordResetRequestDTO extends CommonDTO {

	private Long id;
	private String username;
	private String titleCode;
	private String titleDescription;
	private String name;
	private String statusCode;
	private String statusDescription;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	public String getTitleDescription() {
		return titleDescription;
	}
	public void setTitleDescription(String titleDescription) {
		this.titleDescription = titleDescription;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
