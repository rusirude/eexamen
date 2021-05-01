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
@Table(name = "sys_role_authoriry")
public class SysRoleAuthorityEntity {
	
	private SysRoleAuthorityEntityId sysRoleAuthorityEntityId;
    private SysRoleEntity sysRoleEntity;
    private AuthorityEntity authorityEntity;
    
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "sysRole", column = @Column(name = "sys_role", nullable = false)),
            @AttributeOverride(name = "authority", column = @Column(name = "authority", nullable = false))
    })
	public SysRoleAuthorityEntityId getSysRoleAuthorityEntityId() {
		return sysRoleAuthorityEntityId;
	}
    
	public void setSysRoleAuthorityEntityId(SysRoleAuthorityEntityId sysRoleAuthorityEntityId) {
		this.sysRoleAuthorityEntityId = sysRoleAuthorityEntityId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_role", referencedColumnName = "id", insertable = false, updatable = false)
	public SysRoleEntity getSysRoleEntity() {
		return sysRoleEntity;
	}
	
	public void setSysRoleEntity(SysRoleEntity sysRoleEntity) {
		this.sysRoleEntity = sysRoleEntity;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority", referencedColumnName = "id", insertable = false, updatable = false)
	public AuthorityEntity getAuthorityEntity() {
		return authorityEntity;
	}
	
	public void setAuthorityEntity(AuthorityEntity authorityEntity) {
		this.authorityEntity = authorityEntity;
	}
    
    
}
