package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "question")
public class QuestionEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", length = 10, nullable = false)
    private String code;
    @Column(name = "description", length = 50, nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private StatusEntity statusEntity;
    @Column(name = "typ", length = 12, nullable = false)
    private String type;
    @Column(name = "grp", length = 2, nullable = false)
    private String group;
    @Column(name = "lab", length = 1)
    private String label;
}
