package com.leaf.eexamen.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_user_authority")
public class SysUserAuthorityEntity {

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "sysUser", column = @Column(name = "sys_user", nullable = false)),
			@AttributeOverride(name = "authority", column = @Column(name = "authority", nullable = false))
	})
	private SysUserAuthorityEntityId sysUserAuthorityEntityId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user", referencedColumnName = "username", insertable = false, updatable = false)
	private SysUserEntity sysUserEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authority", referencedColumnName = "id", insertable = false, updatable = false)
	private AuthorityEntity authorityEntity;
	@Column(name = "is_grant", nullable = false)
    private Long isGrant;
}
