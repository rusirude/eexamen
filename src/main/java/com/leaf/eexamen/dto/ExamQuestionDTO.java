package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;

public class ExamQuestionDTO {
    private QuestionDTO question;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_2_FORMAT)
    private String startTime;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_2_FORMAT)
    private String endTime;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_2_FORMAT)
    private String currentTime;
    private String duration;
    private Integer total;
    private Integer done;
    private boolean closed;

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
