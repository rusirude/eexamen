package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "authority")
public class AuthorityEntity extends CommonEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code", length = 10, nullable = false)
	private String code;
	@Column(name = "description", length = 50, nullable = false)
	private String description;
	@Column(name = "auth_code", length = 20, nullable = false)
	private String authCode;
	@Column(name = "url", length = 80, nullable = true)
	private String url;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "section" , nullable = false)
	private SectionEntity sectionEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "status" , nullable = false)
	private StatusEntity statusEntity;
}
