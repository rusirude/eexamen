package com.leaf.eexamen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
public class AuthorityEntity extends CommonEntity{
	private Long id;
	private String code;
	private String description;
	private String authCode;
	private String url;
	private SectionEntity sectionEntity;
	private StatusEntity statusEntity;
	
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
	
	@Column(name = "auth_code", length = 20, nullable = false)
	public String getAuthCode() {
		return authCode;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Column(name = "url", length = 80, nullable = true)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "section" , nullable = false)
	public SectionEntity getSectionEntity() {
		return sectionEntity;
	}

	public void setSectionEntity(SectionEntity sectionEntity) {
		this.sectionEntity = sectionEntity;
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
