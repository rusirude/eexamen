package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "authority")
public class AuthorityEntity extends CommonEntity {
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
    @JoinColumn(name = "section", nullable = false)
    private SectionEntity sectionEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private StatusEntity statusEntity;
}
