package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="sys_user")
public class SysUserEntity extends CommonEntity{
	@Id
	@Column(name  = "username", length = 25 , nullable = false , unique = true)
	private String username;
	@Column(name  = "password", length = 255 , nullable = false)
	private String password;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "title" , nullable = false)
	private TitleEntity titleEntity;
	@Column(name  = "name", length = 100 , nullable = false)
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "status" , nullable = false)
	private StatusEntity statusEntity;
	@Column(name  = "reset_password", nullable = false)
	private Boolean resetRequest;
	@Column(name  = "student", nullable = false)
	private Boolean student;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysUserEntity")
	private Set<SysUserSysRoleEntity> sysUserSysRoleEntities = new HashSet<>();
}
