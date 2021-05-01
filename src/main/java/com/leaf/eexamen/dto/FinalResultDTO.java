package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.Data;

@Data
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
}
