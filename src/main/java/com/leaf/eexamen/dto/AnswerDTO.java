package com.leaf.eexamen.dto;

public class AnswerDTO {
    private Long studentExamination;
    private Integer seq;
    private Long answer;

    public Long getStudentExamination() {
        return studentExamination;
    }

    public void setStudentExamination(Long studentExamination) {
        this.studentExamination = studentExamination;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getAnswer() {
        return answer;
    }

    public void setAnswer(Long answer) {
        this.answer = answer;
    }
}
