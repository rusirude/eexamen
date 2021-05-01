package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorityDTO  extends CommonDTO {

	private String code;
	private String description;
	private String url;
	private String authCode;
	private String sectionCode;
	private String sectionDescription;
	private String statusCode;
	private String statusDescription;
}
