package com.leaf.eexamen.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SysUserSysRoleEntityId implements Serializable{
	

	private static final long serialVersionUID = 9098235365146468593L;
	private String sysUser;
    private Long sysRole;

    @Column(name = "sys_user", nullable = false)
    public String getSysUser() {
        return sysUser;
    }

    public void setSysUser(String sysUser) {
        this.sysUser = sysUser;
    }

    @Column(name = "sys_role", nullable = false)
    public Long getSysRole() {
        return sysRole;
    }

    public void setSysRole(Long sysRole) {
        this.sysRole = sysRole;
    }

	@Override
	public int hashCode() {
		return Objects.hash(sysRole, sysUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysUserSysRoleEntityId other = (SysUserSysRoleEntityId) obj;
		return Objects.equals(sysRole, other.sysRole) && Objects.equals(sysUser, other.sysUser);
	}
    
    
}
