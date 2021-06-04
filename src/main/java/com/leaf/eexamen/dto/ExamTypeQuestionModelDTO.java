package com.leaf.eexamen.dto;

import lombok.Data;

@Data
public class ExamTypeQuestionModelDTO {

    private Long id;
    private String typeCode;
    private String typeDescription;
    private String group;
    private String label;
    private Integer noQuestion;
    private String statusCode;
    private String statusDescription;
}