package com.leaf.eexamen.dto;

import java.util.List;
import java.util.Map;

public class ReportDTO {

    private String reportPath;
    private String reportName;
    private Map<String, Object> reportParams;
    private Map<String, Object> additionalParams;
    private List<?> dtoList;
    private String downloadName;

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Map<String, Object> getReportParams() {
        return reportParams;
    }

    public void setReportParams(Map<String, Object> reportParams) {
        this.reportParams = reportParams;
    }

    public Map<String, Object> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, Object> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public List<?> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<?> dtoList) {
        this.dtoList = dtoList;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }
}
