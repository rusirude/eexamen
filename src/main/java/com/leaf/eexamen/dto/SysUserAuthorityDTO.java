package com.leaf.eexamen.dto;

import lombok.Data;

@Data
public class SysUserAuthorityDTO {

	private String username;
	private String titleDescripton;
	private String name;
	private String authorityCode;
	private String authorityDescription;
	private boolean enable;
}
