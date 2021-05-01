package com.leaf.eexamen.utility;

import com.leaf.eexamen.dto.ReportDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReportUtil implements ServletContextAware {


    @Autowired
    ApplicationContext appContext;

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext arg0) {
        servletContext = arg0;
    }


    public void createReportDownload(HttpServletResponse response, ReportDTO reportDTO) throws Exception {

        // Get report path
        String path = reportDTO.getReportPath() + reportDTO.getReportName();


        // Get download file name
        String downloadFileName = reportDTO.getDownloadName() + ".pdf";

        // Compile report
        JasperReport jasperReport = null;
        try {
            final InputStream stream = this.getClass().getResourceAsStream(path);
            jasperReport = JasperCompileManager.compileReport(stream);

            // Set DTO list
            JRDataSource jRdataSource = new JREmptyDataSource();

            if (reportDTO.getDtoList() != null) {
                jRdataSource = new JRBeanCollectionDataSource(reportDTO.getDtoList(), false);
            }

            // Set parameters
            Map<String, Object> parameters = new HashMap<>();

            if (reportDTO.getReportParams() != null) {
                parameters = reportDTO.getReportParams();
            }


            // Fill report
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jRdataSource);


            response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName);
            response.setContentType("application/pdf");
            OutputStream outputStream = response.getOutputStream();

            // Export report to PDF
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createReportDownloadExcel(HttpServletResponse response, ReportDTO reportDTO) throws Exception {

        // Get report path
        String path = reportDTO.getReportPath() + reportDTO.getReportName();


        // Get download file name
        String downloadFileName = reportDTO.getDownloadName() + ".xlsx";

        // Compile report
        JasperReport jasperReport = null;
        try {
            final InputStream stream = this.getClass().getResourceAsStream(path);
            jasperReport = JasperCompileManager.compileReport(stream);

            // Set DTO list
            JRDataSource jRdataSource = new JREmptyDataSource();

            if (reportDTO.getDtoList() != null) {
                jRdataSource = new JRBeanCollectionDataSource(reportDTO.getDtoList(), false);
            }

            // Set parameters
            Map<String, Object> parameters = new HashMap<>();

            if (reportDTO.getReportParams() != null) {
                parameters = reportDTO.getReportParams();
            }


            // Fill report
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jRdataSource);


            response.setHeader("Content-Disposition", "attachment;filename="+downloadFileName);
            response.setContentType("application/octet-stream");
            OutputStream outputStream = response.getOutputStream();



            // Export report to PDF
            JRXlsxExporter exporter = new JRXlsxExporter();


            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();

            reportConfig.setSheetNames(new String[]{downloadFileName});
            reportConfig.setShowGridLines(true);
            reportConfig.setIgnoreCellBorder(false);
            reportConfig.setWhitePageBackground(false);
            reportConfig.setShrinkToFit(true);
            reportConfig.setRemoveEmptySpaceBetweenRows(true);
            reportConfig.setRemoveEmptySpaceBetweenColumns(true);

            exporter.setConfiguration(reportConfig);
            exporter.exportReport();
        } catch (JRException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public DataSource createReportAsByteStream(ReportDTO reportDTO) {

        DataSource dataSource = null;
        // Get report path
        String path = reportDTO.getReportPath() + reportDTO.getReportName();

        // Compile report
        JasperReport jasperReport = null;
        try {
            final InputStream stream = this.getClass().getResourceAsStream(path);
            jasperReport = JasperCompileManager.compileReport(stream);

            // Set DTO list
            JRDataSource jRdataSource = new JREmptyDataSource();

            if (reportDTO.getDtoList() != null) {
                jRdataSource = new JRBeanCollectionDataSource(reportDTO.getDtoList(), false);
            }

            // Set parameters
            Map<String, Object> parameters = new HashMap<>();

            if (reportDTO.getReportParams() != null) {
                parameters = reportDTO.getReportParams();
            }


            // Fill report
            JasperPrint jasperPrint = null;

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jRdataSource);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            dataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");


        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
        return dataSource;
    }
}
