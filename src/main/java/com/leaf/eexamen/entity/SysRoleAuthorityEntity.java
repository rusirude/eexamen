package com.leaf.eexamen.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;

@Data
@Entity
@Table(name = "sys_role_authoriry")
public class SysRoleAuthorityEntity {

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "sysRole", column = @Column(name = "sys_role", nullable = false)),
			@AttributeOverride(name = "authority", column = @Column(name = "authority", nullable = false))
	})
	private SysRoleAuthorityEntityId sysRoleAuthorityEntityId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_role", referencedColumnName = "id", insertable = false, updatable = false)
	private SysRoleEntity sysRoleEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authority", referencedColumnName = "id", insertable = false, updatable = false)
	private AuthorityEntity authorityEntity;
}
