package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserDTO extends CommonDTO {
	
	private String username;
	private String password;
	private String newPassword;
	private String titleCode;
	private String titleDescription;
	private String name;
	private String statusCode;
	private String statusDescription;
}
