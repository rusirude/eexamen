package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "student_examination_question_answer")
public class StudentExaminationQuestionAnswerEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seq")
    private Integer seq;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "student_examination" , nullable = false)
    private StudentExaminationEntity studentExaminationEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "question" , nullable = false)
    private QuestionEntity questionEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "question_answer" , nullable = false)
    private QuestionAnswerEntity questionAnswerEntity;
    @Column(name = "correct")
    private boolean correct;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "correct_question_answer" , nullable = false)
    private QuestionAnswerEntity correctQuestionAnswerEntity;
}