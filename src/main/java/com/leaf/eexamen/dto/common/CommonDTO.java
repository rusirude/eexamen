package com.leaf.eexamen.dto.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;

public class CommonDTO {
	
	private String createdBy;
	@JsonFormat(pattern = CommonConstant.SYSTEM_DATE_FORMAT)
	private Date createdOn;
	private String updatedBy;
	@JsonFormat(pattern = CommonConstant.SYSTEM_DATE_FORMAT)
	private Date updatedOn;
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
