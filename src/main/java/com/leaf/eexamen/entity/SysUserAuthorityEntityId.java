package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@Data
@Embeddable
public class SysUserAuthorityEntityId implements Serializable{

	@Column(name = "sys_user", nullable = false)
	private String sysUser;
	@Column(name = "authority", nullable = false)
	private Long authority;
}
