package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.dto.common.CommonDTO;
import com.leaf.eexamen.utility.CommonConstant;

public class ExaminationDTO extends CommonDTO {

    private String code;
    private String description;
    private String questionCategoryCode;
    private String questionCategoryDescription;
    private String statusCode;
    private String statusDescription;
    private Integer noQuestion;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_FORMAT)
    private String dateOn;
    private String location;
    private String type;
    private Double passMark;
    private String duration;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_FORMAT)
    private String effectiveOn;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_FORMAT)
    private String expireOn;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestionCategoryCode() {
        return questionCategoryCode;
    }

    public void setQuestionCategoryCode(String questionCategoryCode) {
        this.questionCategoryCode = questionCategoryCode;
    }

    public String getQuestionCategoryDescription() {
        return questionCategoryDescription;
    }

    public void setQuestionCategoryDescription(String questionCategoryDescription) {
        this.questionCategoryDescription = questionCategoryDescription;
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

    public Integer getNoQuestion() {
        return noQuestion;
    }

    public void setNoQuestion(Integer noQuestion) {
        this.noQuestion = noQuestion;
    }

    public String getDateOn() {
        return dateOn;
    }

    public void setDateOn(String dateOn) {
        this.dateOn = dateOn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPassMark() {
        return passMark;
    }

    public void setPassMark(Double passMark) {
        this.passMark = passMark;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEffectiveOn() {
        return effectiveOn;
    }

    public void setEffectiveOn(String effectiveOn) {
        this.effectiveOn = effectiveOn;
    }

    public String getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(String expireOn) {
        this.expireOn = expireOn;
    }
}