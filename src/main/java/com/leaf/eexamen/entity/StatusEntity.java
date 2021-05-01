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
@Table(name = "status")
public class StatusEntity {
	
	private Long id;
	private String code;
	private String description;
	private StatusCategoryEntity statusCategoryEntity;
	
	
	private Set<SysUserEntity> sysUserEntities = new HashSet<>();
	private Set<SysRoleEntity> sysRoleEntities = new HashSet<>();
	private Set<AuthorityEntity> authorityEntities = new HashSet<>();
	private Set<SectionEntity> sectionEntities = new HashSet<>();
	private Set<TitleEntity> titleEntities = new HashSet<>();
	private Set<CityEntity> countryEntities = new HashSet<>();
	private Set<PasswordPolicyEntity> passwordPolicyEntities = new HashSet<>();
	private Set<ExaminationEntity> examinationEntities = new HashSet<>();
	private Set<QuestionAnswerEntity> questionAnswerEntities = new HashSet<>();
	private Set<QuestionEntity> questionEntities = new HashSet<>();
	private Set<QuestionCategoryEntity> questionCategoryEntities = new HashSet<>();

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
	@JoinColumn(name = "status_category",nullable = false)
	public StatusCategoryEntity getStatusCategoryEntity() {
		return statusCategoryEntity;
	}
	
	public void setStatusCategoryEntity(StatusCategoryEntity statusCategoryEntity) {
		this.statusCategoryEntity = statusCategoryEntity;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<SysUserEntity> getSysUserEntities() {
		return sysUserEntities;
	}

	public void setSysUserEntities(Set<SysUserEntity> sysUserEntities) {
		this.sysUserEntities = sysUserEntities;
	}
	
	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<SysRoleEntity> getSysRoleEntities() {
		return sysRoleEntities;
	}

	public void setSysRoleEntities(Set<SysRoleEntity> sysRoleEntities) {
		this.sysRoleEntities = sysRoleEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<AuthorityEntity> getAuthorityEntities() {
		return authorityEntities;
	}

	public void setAuthorityEntities(Set<AuthorityEntity> authorityEntities) {
		this.authorityEntities = authorityEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<SectionEntity> getSectionEntities() {
		return sectionEntities;
	}

	public void setSectionEntities(Set<SectionEntity> sectionEntities) {
		this.sectionEntities = sectionEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<TitleEntity> getTitleEntities() {
		return titleEntities;
	}

	public void setTitleEntities(Set<TitleEntity> titleEntities) {
		this.titleEntities = titleEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<CityEntity> getCountryEntities() {
		return countryEntities;
	}

	public void setCountryEntities(Set<CityEntity> countryEntities) {
		this.countryEntities = countryEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<PasswordPolicyEntity> getPasswordPolicyEntities() {
		return passwordPolicyEntities;
	}

	public void setPasswordPolicyEntities(Set<PasswordPolicyEntity> passwordPolicyEntities) {
		this.passwordPolicyEntities = passwordPolicyEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<ExaminationEntity> getExaminationEntities() {
		return examinationEntities;
	}

	public void setExaminationEntities(Set<ExaminationEntity> examinationEntities) {
		this.examinationEntities = examinationEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<QuestionAnswerEntity> getQuestionAnswerEntities() {
		return questionAnswerEntities;
	}

	public void setQuestionAnswerEntities(Set<QuestionAnswerEntity> questionAnswerEntities) {
		this.questionAnswerEntities = questionAnswerEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<QuestionEntity> getQuestionEntities() {
		return questionEntities;
	}

	public void setQuestionEntities(Set<QuestionEntity> questionEntities) {
		this.questionEntities = questionEntities;
	}

	@OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
	public Set<QuestionCategoryEntity> getQuestionCategoryEntities() {
		return questionCategoryEntities;
	}

	public void setQuestionCategoryEntities(Set<QuestionCategoryEntity> questionCategoryEntities) {
		this.questionCategoryEntities = questionCategoryEntities;
	}
}
