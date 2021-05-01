package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "title")
public class TitleEntity extends CommonEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code", length = 10, nullable = false)
	private String code;
	@Column(name = "description", length = 50, nullable = false)
    private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "status" , nullable = false)
    private StatusEntity statusEntity;
	@OneToMany(mappedBy = "titleEntity", fetch = FetchType.LAZY)
	private Set<SysUserEntity> sysUserEntities = new HashSet<>();
}
