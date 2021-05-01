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
public class SysRoleAuthorityEntityId implements Serializable{

	@Column(name = "sys_role", nullable = false)
	private Long sysRole;
	@Column(name = "authority", nullable = false)
	private Long authority;
}
