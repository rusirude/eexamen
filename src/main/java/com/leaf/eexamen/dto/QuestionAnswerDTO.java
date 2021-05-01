package com.leaf.eexamen.dto;

import lombok.Data;

@Data
public class QuestionAnswerDTO {
    private Long id;
    private Integer position;
    private String description;
    private String statusCode;
    private String statusDescription;
    private boolean correct;
    private boolean mark;
}