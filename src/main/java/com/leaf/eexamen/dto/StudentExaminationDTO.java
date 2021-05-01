package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;

public class StudentExaminationDTO {
    private Long id;
    private String student;
    private String studentName;
    private String company;
    private String examinationCode;
    private String examinationDescription;
    private Integer noQuestion;
    private String duration;
    private String statusCode;
    private String statusDescription;
    private String pass;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_FORMAT)
    private String dateOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getExaminationCode() {
        return examinationCode;
    }

    public void setExaminationCode(String examinationCode) {
        this.examinationCode = examinationCode;
    }

    public String getExaminationDescription() {
        return examinationDescription;
    }

    public void setExaminationDescription(String examinationDescription) {
        this.examinationDescription = examinationDescription;
    }

    public Integer getNoQuestion() {
        return noQuestion;
    }

    public void setNoQuestion(Integer noQuestion) {
        this.noQuestion = noQuestion;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDateOn() {
        return dateOn;
    }

    public void setDateOn(String dateOn) {
        this.dateOn = dateOn;
    }
}
