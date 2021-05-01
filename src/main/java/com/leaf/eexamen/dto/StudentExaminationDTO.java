package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.Data;

@Data
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
}
