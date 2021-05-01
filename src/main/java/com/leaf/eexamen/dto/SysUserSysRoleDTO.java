package com.leaf.eexamen.dto;

import lombok.Data;

@Data
public class SysUserSysRoleDTO {
	private String username;
	private String name;
	private String sysRoleCode;
	private String sysRoleDescription;	
	private boolean enable;
	
}
