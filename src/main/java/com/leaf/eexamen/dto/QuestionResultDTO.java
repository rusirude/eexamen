package com.leaf.eexamen.dto;

import lombok.Data;

@Data
public class QuestionResultDTO {
    private String question;
    private String answer;
    private String correctAnswer;
    private String result;
    private Boolean correct;
}

