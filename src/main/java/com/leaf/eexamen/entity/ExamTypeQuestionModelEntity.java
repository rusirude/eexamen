package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "exam_type_question_model")
public class ExamTypeQuestionModelEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_type", nullable = false)
    private ExamTypeEntity examTypeEntity;
    @Column(name = "typ", length = 12, nullable = false)
    private String type;
    @Column(name = "grp", length = 2, nullable = false)
    private String group;
    @Column(name = "lab", length = 1)
    private String label;
    @Column(name = "no_question")
    private Integer noQuestion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private StatusEntity statusEntity;
}
