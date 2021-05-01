package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;

public class FinalResultDTO {
    private Integer finalMark;
    private int correct;
    private int wrong;
    private int notAnswered;
    private int total;
    private String name;
    private String location;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_2_FORMAT)
    private String dateOn;
    private String type;
    private String pass;
    private Boolean passed;

    public Integer getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(Integer finalMark) {
        this.finalMark = finalMark;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getNotAnswered() {
        return notAnswered;
    }

    public void setNotAnswered(int notAnswered) {
        this.notAnswered = notAnswered;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOn() {
        return dateOn;
    }

    public void setDateOn(String dateOn) {
        this.dateOn = dateOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }
}
