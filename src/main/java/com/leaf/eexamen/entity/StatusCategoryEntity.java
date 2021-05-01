package com.leaf.eexamen.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "status_category")
public class StatusCategoryEntity {
	private Long id;
	private String code;
	private String description;
	
	private Set<StatusEntity> statusEntities = new HashSet<>();
	
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

	@OneToMany(mappedBy = "statusCategoryEntity", fetch = FetchType.LAZY)
	public Set<StatusEntity> getStatusEntities() {
		return statusEntities;
	}

	public void setStatusEntities(Set<StatusEntity> statusEntities) {
		this.statusEntities = statusEntities;
	}
	
	
	
	
}
