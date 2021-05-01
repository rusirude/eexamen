package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "password_reset_request")
public class PasswordResetRequestEntity extends CommonEntity{
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "sys_user" , nullable = false)
	private SysUserEntity sysUserEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "status" , nullable = false)
    private StatusEntity statusEntity;
}
