package com.leaf.eexamen.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SysUserAuthorityEntityId implements Serializable{
	

	private static final long serialVersionUID = -3414456477580068500L;
	private String sysUser;
	private Long authority;
	
	@Column(name = "sys_user", nullable = false)
	public String getSysUser() {
		return sysUser;
	}
	
	public void setSysUser(String sysUser) {
		this.sysUser = sysUser;
	}
	
	@Column(name = "authority", nullable = false)
	public Long getAuthority() {
		return authority;
	}
	
	public void setAuthority(Long authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authority, sysUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysUserAuthorityEntityId other = (SysUserAuthorityEntityId) obj;
		return Objects.equals(authority, other.authority) && Objects.equals(sysUser, other.sysUser);
	}

	
	
		 
	 
}
