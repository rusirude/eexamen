package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.Data;

@Data
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
}
