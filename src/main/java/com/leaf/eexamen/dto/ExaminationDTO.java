package com.leaf.eexamen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaf.eexamen.dto.common.CommonDTO;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExaminationDTO extends CommonDTO {

    private String code;
    private String description;
    private String statusCode;
    private String statusDescription;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_FORMAT)
    private String dateOn;
    private String location;
    private String examTypeCode;
    private String examTypeDescription;
    private String duration;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_FORMAT)
    private String effectiveOn;
    @JsonFormat(pattern = CommonConstant.SYSTEM_DATE_TIME_FORMAT)
    private String expireOn;
}