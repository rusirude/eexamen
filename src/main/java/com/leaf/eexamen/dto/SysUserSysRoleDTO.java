package com.leaf.eexamen.dto;

public class SysUserSysRoleDTO {
	private String username;
	private String name;
	private String sysRoleCode;
	private String sysRoleDescription;	
	private boolean enable;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSysRoleCode() {
		return sysRoleCode;
	}
	public void setSysRoleCode(String sysRoleCode) {
		this.sysRoleCode = sysRoleCode;
	}
	public String getSysRoleDescription() {
		return sysRoleDescription;
	}	
	public void setSysRoleDescription(String sysRoleDescription) {
		this.sysRoleDescription = sysRoleDescription;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	
}
