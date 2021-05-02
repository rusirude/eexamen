package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
