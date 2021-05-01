package com.leaf.eexamen.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReportDTO {

    private String reportPath;
    private String reportName;
    private Map<String, Object> reportParams;
    private Map<String, Object> additionalParams;
    private List<?> dtoList;
    private String downloadName;
}
