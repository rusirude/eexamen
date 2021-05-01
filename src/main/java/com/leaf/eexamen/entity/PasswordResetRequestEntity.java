package com.leaf.eexamen.entity;

import javax.persistence.*;

@Entity
@Table(name = "password_reset_request")
public class PasswordResetRequestEntity extends CommonEntity{
	
	private Long id;
	private SysUserEntity sysUserEntity;
    private StatusEntity statusEntity;  


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "sys_user" , nullable = false)
	public SysUserEntity getSysUserEntity() {
		return sysUserEntity;
	}

	public void setSysUserEntity(SysUserEntity sysUserEntity) {
		this.sysUserEntity = sysUserEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "status" , nullable = false)
	public StatusEntity getStatusEntity() {
		return statusEntity;
	}
	
	public void setStatusEntity(StatusEntity statusEntity) {
		this.statusEntity = statusEntity;
	}

}
