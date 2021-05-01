package com.leaf.eexamen.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "sys_user_sys_role")
public class SysUserSysRoleEntity {

	private SysUserSysRoleEntityId sysUserSysRoleEntityId;
    private SysUserEntity sysUserEntity;
    private SysRoleEntity sysRoleEntity;
    
    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "sysUser", column = @Column(name = "sys_user", nullable = false)),
        @AttributeOverride(name = "sysRole", column = @Column(name = "sys_role", nullable = false))
    })    
	public SysUserSysRoleEntityId getSysUserSysRoleEntityId() {
		return sysUserSysRoleEntityId;
	}
    
	public void setSysUserSysRoleEntityId(SysUserSysRoleEntityId sysUserSysRoleEntityId) {
		this.sysUserSysRoleEntityId = sysUserSysRoleEntityId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user", referencedColumnName = "username", insertable = false, updatable = false)
	public SysUserEntity getSysUserEntity() {
		return sysUserEntity;
	}
	
	public void setSysUserEntity(SysUserEntity sysUserEntity) {
		this.sysUserEntity = sysUserEntity;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sys_role", referencedColumnName = "id", insertable = false, updatable = false)
	public SysRoleEntity getSysRoleEntity() {
		return sysRoleEntity;
	}
	
	public void setSysRoleEntity(SysRoleEntity sysRoleEntity) {
		this.sysRoleEntity = sysRoleEntity;
	}
    
    
}
