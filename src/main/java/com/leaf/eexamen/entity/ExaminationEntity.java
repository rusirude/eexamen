package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "examination")
public class ExaminationEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", length = 10, nullable = false)
    private String code;
    @Column(name = "description", length = 50, nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_category", nullable = false)
    private QuestionCategoryEntity questionCategoryEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private StatusEntity statusEntity;
    @Column(name = "no_question")
    private Integer noQuestion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_on")
    private Date dateOn;
    @Column(name = "location")
    private String location;
    @Column(name = "type")
    private String type;
    @Column(name = "pass_mark")
    private Double passMark;
    @Column(name = "duration")
    private String duration;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effective_on")
    private Date effectiveOn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expier_on")
    private Date expireOn;
}
