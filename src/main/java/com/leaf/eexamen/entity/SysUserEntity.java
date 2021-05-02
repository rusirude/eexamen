package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class SysUserEntity extends CommonEntity {
    @Id
    @Column(name = "username", length = 25, nullable = false, unique = true)
    private String username;
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title", nullable = false)
    private TitleEntity titleEntity;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private StatusEntity statusEntity;
    @Column(name = "reset_password", nullable = false)
    private Boolean resetRequest;
    @Column(name = "student", nullable = false)
    private Boolean student;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sysUserEntity")
    private Set<SysUserSysRoleEntity> sysUserSysRoleEntities = new HashSet<>();
}
