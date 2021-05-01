package com.leaf.eexamen.dto;

public class SysUserAuthorityDTO {

	private String username;
	private String titleDescripton;
	private String name;
	private String authorityCode;
	private String authorityDescription;
	private boolean enable;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitleDescripton() {
		return titleDescripton;
	}
	public void setTitleDescripton(String titleDescripton) {
		this.titleDescripton = titleDescripton;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public String getAuthorityDescription() {
		return authorityDescription;
	}
	public void setAuthorityDescription(String authorityDescription) {
		this.authorityDescription = authorityDescription;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	
	
}
