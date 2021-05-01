package com.leaf.eexamen.dto;

import lombok.Data;

@Data
public class SysRoleAuthorityDTO {
	
	private String sysRoleCode;
	private String sysRoleDescription;
	private String authorityCode;
	private String authorityDescription;
	private boolean enable;

}
