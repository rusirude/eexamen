package com.leaf.eexamen.dto.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.Data;

@Data
public class CommonDTO {
	
	private String createdBy;
	@JsonFormat(pattern = CommonConstant.SYSTEM_DATE_FORMAT)
	private Date createdOn;
	private String updatedBy;
	@JsonFormat(pattern = CommonConstant.SYSTEM_DATE_FORMAT)
	private Date updatedOn;
}
