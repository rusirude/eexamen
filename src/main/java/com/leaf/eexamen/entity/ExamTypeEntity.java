package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "exam_type")
public class ExamTypeEntity extends CommonEntity {

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
    @Column(name = "exam_category", length = 12, nullable = false)
    private String examCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_category", nullable = false)
    private QuestionCategoryEntity questionCategoryEntity;
    @Column(name = "t_pass_mark")
    private Double tPassMark;
    @Column(name = "w_pass_mark")
    private Double wPassMark;
}
