package com.leaf.eexamen.entity;

import javax.persistence.*;

@Entity
@Table(name = "student_examination_question_answer")
public class StudentExaminationQuestionAnswerEntity extends CommonEntity {

    private Long id;
    private Integer seq;
    private StudentExaminationEntity studentExaminationEntity;
    private QuestionEntity questionEntity;
    private QuestionAnswerEntity questionAnswerEntity;
    private boolean correct;
    private QuestionAnswerEntity correctQuestionAnswerEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "seq")
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "student_examination" , nullable = false)
    public StudentExaminationEntity getStudentExaminationEntity() {
        return studentExaminationEntity;
    }

    public void setStudentExaminationEntity(StudentExaminationEntity studentExaminationEntity) {
        this.studentExaminationEntity = studentExaminationEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "question" , nullable = false)
    public QuestionEntity getQuestionEntity() {
        return questionEntity;
    }

    public void setQuestionEntity(QuestionEntity questionEntity) {
        this.questionEntity = questionEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "question_answer" , nullable = false)
    public QuestionAnswerEntity getQuestionAnswerEntity() {
        return questionAnswerEntity;
    }

    public void setQuestionAnswerEntity(QuestionAnswerEntity questionAnswerEntity) {
        this.questionAnswerEntity = questionAnswerEntity;
    }

    @Column(name = "correct")
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "correct_question_answer" , nullable = false)
    public QuestionAnswerEntity getCorrectQuestionAnswerEntity() {
        return correctQuestionAnswerEntity;
    }

    public void setCorrectQuestionAnswerEntity(QuestionAnswerEntity correctQuestionAnswerEntity) {
        this.correctQuestionAnswerEntity = correctQuestionAnswerEntity;
    }
}