package com.leaf.eexamen.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user_authority")
public class SysUserAuthorityEntity {
	
	private SysUserAuthorityEntityId sysUserAuthorityEntityId;
    private SysUserEntity sysUserEntity;
    private AuthorityEntity authorityEntity;
    private Long isGrant;
    
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "sysUser", column = @Column(name = "sys_user", nullable = false)),
            @AttributeOverride(name = "authority", column = @Column(name = "authority", nullable = false))
    })
	public SysUserAuthorityEntityId getSysUserAuthorityEntityId() {
		return sysUserAuthorityEntityId;
	}
    
	public void setSysUserAuthorityEntityId(SysUserAuthorityEntityId sysUserAuthorityEntityId) {
		this.sysUserAuthorityEntityId = sysUserAuthorityEntityId;
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
    @JoinColumn(name = "authority", referencedColumnName = "id", insertable = false, updatable = false)
	public AuthorityEntity getAuthorityEntity() {
		return authorityEntity;
	}
	
	public void setAuthorityEntity(AuthorityEntity authorityEntity) {
		this.authorityEntity = authorityEntity;
	}

	@Column(name = "is_grant", nullable = false)
	public Long getIsGrant() {
		return isGrant;
	}

	public void setIsGrant(Long isGrant) {
		this.isGrant = isGrant;
	}
    
	
	
    
}
