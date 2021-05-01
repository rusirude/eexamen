package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.*;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.HashMap;
import java.util.List;

public interface StartExaminationService {

    DataTableResponseDTO getStudentExaminationForDataTable(DataTableRequestDTO dataTableRequestDTO);

    ResponseDTO<Integer> setupQuestionForExam(Long id);

    ResponseDTO<ExamQuestionDTO> getQuestionsForExamination(Long studentExam, Integer seq);

    ResponseDTO<?> saveAnswer(AnswerDTO answerDTO);

    void saveFinalAnswerCalculation(Long studentExam);

    ResponseDTO<List<RemainingQuestion>> getRemainingQuestions(Long studentExam);

    ResponseDTO<FinalResultDTO> getFinalResult(Long studentExam);

    ReportDTO generateAnswerDetailReportDetails(long studentExam);

    ReportDTO generateAnswerDetailReportDetails(String sortColumn, String sortOrder, String search);

    ResponseDTO<?> generateAnswerDetailReportDetailsAndSendMail(long studentExam);
;
    DataTableResponseDTO getStudentExaminationForDataTableForAdd(DataTableRequestDTO dataTableRequestDTO);

    ResponseDTO<HashMap<String, Object>> getReferenceDataForStudentExaminationAdd();

    ResponseDTO<?> saveStudentExamination(StudentExaminationDTO studentExaminationDTO);

    ResponseDTO<?> deleteStudentExamination(StudentExaminationDTO studentExaminationDTO);

    ResponseDTO<?> tryToCloseStudentExamination(StudentExaminationDTO studentExaminationDTO);
}
