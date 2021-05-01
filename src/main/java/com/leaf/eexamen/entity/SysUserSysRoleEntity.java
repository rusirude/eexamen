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
@Table(name = "sys_user_sys_role")
public class SysUserSysRoleEntity {

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "sysUser", column = @Column(name = "sys_user", nullable = false)),
			@AttributeOverride(name = "sysRole", column = @Column(name = "sys_role", nullable = false))
	})
	private SysUserSysRoleEntityId sysUserSysRoleEntityId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user", referencedColumnName = "username", insertable = false, updatable = false)
	private SysUserEntity sysUserEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_role", referencedColumnName = "id", insertable = false, updatable = false)
	private SysRoleEntity sysRoleEntity;
}
