package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="master_data")
public class MasterDataEntity extends CommonEntity {
	@Id
	@Column(name  = "code", nullable = false , unique = true)
	private String code;
	@Column(name  = "value", nullable = true)
	private String value;
}
