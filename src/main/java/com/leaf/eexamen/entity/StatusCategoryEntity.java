package com.leaf.eexamen.entity;

import lombok.Data;

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


@Data
@Entity
@Table(name = "status_category")
public class StatusCategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code", length = 10, nullable = false)
	private String code;
	@Column(name = "description", length = 50, nullable = false)
	private String description;
	@OneToMany(mappedBy = "statusCategoryEntity", fetch = FetchType.LAZY)
	private Set<StatusEntity> statusEntities = new HashSet<>();
	
}
