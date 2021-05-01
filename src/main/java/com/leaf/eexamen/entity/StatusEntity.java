package com.leaf.eexamen.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "status")
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", length = 10, nullable = false)
    private String code;
    @Column(name = "description", length = 50, nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_category", nullable = false)
    private StatusCategoryEntity statusCategoryEntity;

    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<SysUserEntity> sysUserEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<SysRoleEntity> sysRoleEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<AuthorityEntity> authorityEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<SectionEntity> sectionEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<TitleEntity> titleEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<CityEntity> countryEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<PasswordPolicyEntity> passwordPolicyEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<ExaminationEntity> examinationEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<QuestionAnswerEntity> questionAnswerEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<QuestionEntity> questionEntities = new HashSet<>();
    @OneToMany(mappedBy = "statusEntity", fetch = FetchType.LAZY)
    private Set<QuestionCategoryEntity> questionCategoryEntities = new HashSet<>();
}
