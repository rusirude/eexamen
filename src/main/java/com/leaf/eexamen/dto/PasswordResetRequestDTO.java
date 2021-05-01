package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordResetRequestDTO extends CommonDTO {

	private Long id;
	private String username;
	private String titleCode;
	private String titleDescription;
	private String name;
	private String statusCode;
	private String statusDescription;
}
