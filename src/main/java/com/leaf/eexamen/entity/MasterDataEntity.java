package com.leaf.eexamen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_data")
public class MasterDataEntity extends CommonEntity {
	private String code;
	private String value;
	
	@Id
	@Column(name  = "code", nullable = false , unique = true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name  = "value", nullable = true)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
