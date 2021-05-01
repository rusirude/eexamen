package com.leaf.eexamen.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "title")
public class TitleEntity extends CommonEntity{
	
	private Long id;
    private String code;
    private String description;
    private StatusEntity statusEntity;  
    
    private Set<SysUserEntity> sysUserEntities = new HashSet<>();
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "code", length = 10, nullable = false)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "description", length = 50, nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "status" , nullable = false)
	public StatusEntity getStatusEntity() {
		return statusEntity;
	}
	
	public void setStatusEntity(StatusEntity statusEntity) {
		this.statusEntity = statusEntity;
	}
	
	@OneToMany(mappedBy = "titleEntity", fetch = FetchType.LAZY)
	public Set<SysUserEntity> getSysUserEntities() {
		return sysUserEntities;
	}

	public void setSysUserEntities(Set<SysUserEntity> sysUserEntities) {
		this.sysUserEntities = sysUserEntities;
	}

}
