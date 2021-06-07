package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.Data;

@Data
public class FinalResultDTO {
    private Integer tFinalMark;
    private int tCorrect;
    private int tWrong;
    private int tNotAnswered;
    private int tTotal;
    private Integer wFinalMark;
    private int wCorrect;
    private int wWrong;
    private int wNotAnswered;
    private int wTotal;
    private String name;
    private String location;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_2_FORMAT)
    private String dateOn;
    private String type;
    private String pass;
    private Boolean passed;
}
