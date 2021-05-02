package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.*;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.*;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.enums.EmailEnum;
import com.leaf.eexamen.enums.ExamStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.service.StartExaminationService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import com.leaf.eexamen.utility.MailSenderService;
import com.leaf.eexamen.utility.ReportUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StartExaminationServiceImpl implements StartExaminationService {

    private StudentExaminationDAO studentExaminationDAO;
    private StatusDAO statusDAO;
    private QuestionDAO questionDAO;
    private QuestionAnswerDAO questionAnswerDAO;
    private StudentExaminationQuestionAnswerDAO studentExaminationQuestionAnswerDAO;
    private StudentDAO studentDAO;
    private SysUserDAO sysUserDAO;
    private ExaminationDAO examinationDAO;
    private CommonMethod commonMethod;
    private MailSenderService mailSenderService;
    private EmailBodyDAO emailBodyDAO;
    private ReportUtil reportUtil;


    @Override
    @Transactional
    public DataTableResponseDTO getStudentExaminationForDataTable(DataTableRequestDTO dataTableRequestDTO) {
        List<StudentExaminationDTO> list = new ArrayList<>();
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord = Long.valueOf(0);
        dataTableRequestDTO.setSearch(commonMethod.getUsername());
        try {
            list = studentExaminationDAO.<List<StudentExaminationEntity>>getDataForGridForStudentBetweenSystemDate(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST, Arrays.asList(ExamStatusEnum.PENDING.getCode(), ExamStatusEnum.START.getCode(), ExamStatusEnum.CLOSED.getCode()), commonMethod.getSystemDate())
                    .stream().map(entity -> {
                        StudentExaminationDTO dto = new StudentExaminationDTO();
                        dto.setId(entity.getId());
                        dto.setStudent(entity.getSysUserEntity().getUsername());
                        dto.setExaminationCode(entity.getExaminationEntity().getCode());
                        dto.setExaminationDescription(entity.getExaminationEntity().getDescription());
                        dto.setDuration(entity.getExaminationEntity().getDuration());
                        dto.setNoQuestion(entity.getExaminationEntity().getNoQuestion());
                        dto.setStatusCode(entity.getStatusEntity().getCode());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = studentExaminationDAO.<Long>getDataForGridForStudentBetweenSystemDate(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT, Arrays.asList(ExamStatusEnum.PENDING.getCode(), ExamStatusEnum.START.getCode(), ExamStatusEnum.CLOSED.getCode()), commonMethod.getSystemDate());

            responseDTO.setData(list);
            responseDTO.setRecordsTotal(numOfRecord);
            responseDTO.setRecordsFiltered(numOfRecord);
            responseDTO.setDraw(dataTableRequestDTO.getDraw());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseDTO;
    }


    @Override
    @Transactional
    public ResponseDTO<Integer> setupQuestionForExam(Long id) {

        String code = ResponseCodeEnum.FAILED.getCode();
        AtomicInteger i = new AtomicInteger(0);
        int seq = 1;
        try {
            Date systemDate = commonMethod.getSystemDate();
            StudentExaminationEntity studentExaminationEntity = studentExaminationDAO.findStudentExaminationEntity(id);
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DefaultStatusEnum.ACTIVE.getCode());
            StatusEntity startExamStatusEntity = statusDAO.findStatusEntityByCode(ExamStatusEnum.START.getCode());
            StatusEntity closedExamStatusEntity = statusDAO.findStatusEntityByCode(ExamStatusEnum.CLOSED.getCode());

            if (ExamStatusEnum.PENDING.getCode().equals(studentExaminationEntity.getStatusEntity().getCode())) {
                questionDAO.findAllQuestionEntitiesRandomly(statusEntity.getId(), studentExaminationEntity.getExaminationEntity().getNoQuestion(), studentExaminationEntity.getExaminationEntity().getQuestionCategoryEntity().getId())
                        .forEach(questionEntity -> {
                            StudentExaminationQuestionAnswerEntity questionAnswerEntity = new StudentExaminationQuestionAnswerEntity();
                            questionAnswerEntity.setStudentExaminationEntity(studentExaminationEntity);
                            questionAnswerEntity.setQuestionEntity(questionEntity);
                            questionAnswerEntity.setSeq(i.incrementAndGet());

                            commonMethod.getPopulateEntityWhenInsert(questionAnswerEntity);
                            studentExaminationQuestionAnswerDAO.saveStudentExaminationQuestionAnswerEntity(questionAnswerEntity);

                        });

                String[] duration = studentExaminationEntity.getExaminationEntity().getDuration().split(":");
                Date expectedTime = commonMethod.addHoursAndMinutesToDate(systemDate, Integer.parseInt(duration[0]), Integer.parseInt(duration[1]));
                Date endDateTime = getEndDateTimeForExam(expectedTime, studentExaminationEntity.getExaminationEntity().getExpireOn());

                studentExaminationEntity.setStatusEntity(startExamStatusEntity);
                studentExaminationEntity.setPassMark(studentExaminationEntity.getExaminationEntity().getPassMark());
                studentExaminationEntity.setStartOn(systemDate);
                studentExaminationEntity.setEndOn(endDateTime);

                commonMethod.getPopulateEntityWhenUpdate(studentExaminationEntity);
                studentExaminationDAO.updateStudentExaminationEntity(studentExaminationEntity);
            } else if (ExamStatusEnum.START.getCode().equals(studentExaminationEntity.getStatusEntity().getCode())) {


                String[] duration = studentExaminationEntity.getExaminationEntity().getDuration().split(":");
                Date expectedTime = commonMethod.addHoursAndMinutesToDate(studentExaminationEntity.getStartOn(), Integer.parseInt(duration[0]), Integer.parseInt(duration[1]));
                Date endDateTime = getEndDateTimeForExam(expectedTime, studentExaminationEntity.getExaminationEntity().getExpireOn());

                studentExaminationEntity.setEndOn(endDateTime);
                if (systemDate.compareTo(endDateTime) < 1) {
                    StudentExaminationQuestionAnswerEntity studentAnswerEntity = Optional.ofNullable(studentExaminationQuestionAnswerDAO.findFirstStudentExaminationQuestionAnswerEntityByStudentExaminationAndAnswerIsNull(id))
                            .orElse(new StudentExaminationQuestionAnswerEntity());
                    seq = Optional.ofNullable(studentAnswerEntity.getSeq()).orElse(1);
                } else {
                    studentExaminationEntity.setStatusEntity(closedExamStatusEntity);
                    calculateAnswer(studentExaminationEntity);
                    seq = 0;
                }

                commonMethod.getPopulateEntityWhenUpdate(studentExaminationEntity);
                studentExaminationDAO.updateStudentExaminationEntity(studentExaminationEntity);

            } else {
                calculateAnswer(studentExaminationEntity);
                seq = 0;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseDTO<>(code, seq);
    }

    @Override
    @Transactional
    public ResponseDTO<ExamQuestionDTO> getQuestionsForExamination(Long studentExam, Integer seq) {
        String code = ResponseCodeEnum.FAILED.getCode();
        QuestionDTO questionDTO = null;
        ExamQuestionDTO examQuestionDTO = null;
        try {
            Date systemDate = commonMethod.getSystemDate();
            StudentExaminationQuestionAnswerEntity studentQuestionAnswerEntity = studentExaminationQuestionAnswerDAO.findStudentExaminationQuestionAnswerEntityByStudentExaminationAndSeq(studentExam, seq);
            Integer notAnswer = Optional.ofNullable(studentExaminationQuestionAnswerDAO.findStudentExaminationQuestionAnswerEntityByStudentExaminationAndAnswerIsNull(studentExam))
                    .orElse(Collections.emptyList()).size();
            StudentExaminationEntity studentExaminationEntity = studentQuestionAnswerEntity.getStudentExaminationEntity();
            StatusEntity closedExamStatusEntity = statusDAO.findStatusEntityByCode(ExamStatusEnum.CLOSED.getCode());

            String[] duration = studentExaminationEntity.getExaminationEntity().getDuration().split(":");
            Date endDateTime = commonMethod.addHoursAndMinutesToDate(studentExaminationEntity.getStartOn(), Integer.parseInt(duration[0]), Integer.parseInt(duration[1]));
            studentExaminationEntity.setEndOn(endDateTime);


            questionDTO = new QuestionDTO();
            questionDTO.setCode(studentQuestionAnswerEntity.getQuestionEntity().getCode());
            questionDTO.setDescription(studentQuestionAnswerEntity.getQuestionEntity().getDescription());

            if (systemDate.compareTo(endDateTime) < 1) {
                List<QuestionAnswerDTO> questionAnswerDTOs = questionAnswerDAO.findAllQuestionAnswerEntitiesByQuestion(studentQuestionAnswerEntity.getQuestionEntity().getId(), DefaultStatusEnum.ACTIVE.getCode())
                        .stream()
                        .sorted(Comparator.comparing(QuestionAnswerEntity::getPosition))
                        .map(questionAnswerEntity -> {
                            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
                            questionAnswerDTO.setId(questionAnswerEntity.getId());
                            questionAnswerDTO.setDescription(questionAnswerEntity.getDescription());
                            boolean isMark = Objects.nonNull(studentQuestionAnswerEntity.getQuestionAnswerEntity()) && (studentQuestionAnswerEntity.getQuestionAnswerEntity().getId().equals(questionAnswerEntity.getId()));
                            questionAnswerDTO.setMark(isMark);
                            return questionAnswerDTO;
                        })
                        .collect(Collectors.toList());

                questionDTO.setQuestionAnswers(questionAnswerDTOs);

                examQuestionDTO = new ExamQuestionDTO();
                examQuestionDTO.setQuestion(questionDTO);
                examQuestionDTO.setDuration(studentQuestionAnswerEntity.getStudentExaminationEntity().getExaminationEntity().getDuration());
                examQuestionDTO.setTotal(studentQuestionAnswerEntity.getStudentExaminationEntity().getExaminationEntity().getNoQuestion());
                examQuestionDTO.setDone(studentQuestionAnswerEntity.getStudentExaminationEntity().getExaminationEntity().getNoQuestion() - notAnswer);
                examQuestionDTO.setStartTime(commonMethod.dateTimeToString(studentQuestionAnswerEntity.getStudentExaminationEntity().getStartOn()));
                examQuestionDTO.setEndTime(commonMethod.dateTimeToString(studentQuestionAnswerEntity.getStudentExaminationEntity().getEndOn()));
                examQuestionDTO.setCurrentTime(commonMethod.dateTimeToString(systemDate, CommonConstant.SYSTEM_DATE_TIME_3_FORMAT));
                code = ResponseCodeEnum.SUCCESS.getCode();
            } else {
                studentExaminationEntity.setStatusEntity(closedExamStatusEntity);
                calculateAnswer(studentExaminationEntity);
                examQuestionDTO = new ExamQuestionDTO();
                examQuestionDTO.setClosed(true);
                code = ResponseCodeEnum.SUCCESS.getCode();
            }

            commonMethod.getPopulateEntityWhenUpdate(studentExaminationEntity);
            studentExaminationDAO.updateStudentExaminationEntity(studentExaminationEntity);


        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, examQuestionDTO);
    }

    @Override
    @Transactional
    public ResponseDTO<?> saveAnswer(AnswerDTO answerDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        try {
            StudentExaminationQuestionAnswerEntity studentQuestionAnswerEntity = studentExaminationQuestionAnswerDAO.findStudentExaminationQuestionAnswerEntityByStudentExaminationAndSeq(answerDTO.getStudentExamination(), answerDTO.getSeq());
            QuestionAnswerEntity questionAnswerEntity = questionAnswerDAO.loadQuestionAnswerEntity(answerDTO.getAnswer());
            studentQuestionAnswerEntity.setQuestionAnswerEntity(questionAnswerEntity);

            commonMethod.getPopulateEntityWhenUpdate(studentQuestionAnswerEntity);
            studentExaminationQuestionAnswerDAO.updateStudentExaminationQuestionAnswerEntity(studentQuestionAnswerEntity);

            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code);
    }

    @Override
    @Transactional
    public void saveFinalAnswerCalculation(Long studentExam) {
        try {
            finalStateChangeStudentExamination(studentExam);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @Override
    @Transactional
    public ResponseDTO<List<RemainingQuestion>> getRemainingQuestions(Long studentExam) {
        String code = ResponseCodeEnum.FAILED.getCode();
        List<RemainingQuestion> notAnswered = null;
        try {
            notAnswered = studentExaminationQuestionAnswerDAO.findStudentExaminationQuestionAnswerEntityByStudentExaminationAndAnswerIsNull(studentExam)
                    .stream()
                    .map(entity -> {
                        RemainingQuestion remainingQuestion = new RemainingQuestion();
                        remainingQuestion.setSeq(entity.getSeq());
                        remainingQuestion.setQuestion(entity.getQuestionEntity().getDescription());
                        return remainingQuestion;
                    }).collect(Collectors.toList());
            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseDTO<>(code, notAnswered);
    }


    @Override
    @Transactional
    public ResponseDTO<FinalResultDTO> getFinalResult(Long studentExam) {
        String code = ResponseCodeEnum.FAILED.getCode();
        final FinalResultDTO finalResultDTO = new FinalResultDTO();
        try {
            StudentExaminationEntity studentExaminationEntity = studentExaminationDAO.findStudentExaminationEntity(studentExam);
            studentExaminationQuestionAnswerDAO.findStudentExaminationQuestionAnswerEntityByStudentExamination(studentExam)
                    .forEach(studentExaminationQuestionAnswerEntity -> {
                        finalResultDTO.setTotal(finalResultDTO.getTotal() + 1);
                        if (Objects.isNull(studentExaminationQuestionAnswerEntity.getQuestionAnswerEntity()))
                            finalResultDTO.setNotAnswered(finalResultDTO.getNotAnswered() + 1);
                        else if (studentExaminationQuestionAnswerEntity.isCorrect())
                            finalResultDTO.setCorrect(finalResultDTO.getCorrect() + 1);
                        else
                            finalResultDTO.setWrong(finalResultDTO.getWrong() + 1);
                    });
            if (finalResultDTO.getTotal() > 0) {
                int result = BigDecimal.valueOf(((double) finalResultDTO.getCorrect() / (double) finalResultDTO.getTotal()) * 100)
                        .setScale(0, RoundingMode.HALF_UP).toBigInteger().intValue();
                finalResultDTO.setFinalMark(result);
            } else
                finalResultDTO.setFinalMark(0);

            finalResultDTO.setName(studentExaminationEntity.getSysUserEntity().getTitleEntity().getDescription() + studentExaminationEntity.getSysUserEntity().getName());
            finalResultDTO.setLocation(studentExaminationEntity.getExaminationEntity().getLocation());
            finalResultDTO.setDateOn(commonMethod.dateTimeToString(studentExaminationEntity.getExaminationEntity().getDateOn()));
            finalResultDTO.setType(studentExaminationEntity.getExaminationEntity().getType());
            finalResultDTO.setPass(Optional.ofNullable(studentExaminationEntity.getPass()).orElse(false) ? "Geslaagd" : "Niet geslaagd");
            finalResultDTO.setPassed(Optional.ofNullable(studentExaminationEntity.getPass()).orElse(false));
            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, finalResultDTO);
    }

    @Override
    @Transactional
    public ReportDTO generateAnswerDetailReportDetails(long studentExam) {
        ReportDTO reportDTO = null;
        try {
            reportDTO = getAnswerReport(studentExam);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return reportDTO;
    }

    @Override
    public ReportDTO generateAnswerDetailReportDetails(String sortColumn, String sortOrder, String search) {
        ReportDTO reportDTO = null;
        try {
            reportDTO = new ReportDTO();
            reportDTO.setReportPath(CommonConstant.REPORT_PATH);
            DataTableRequestDTO dataTableRequestDTO = new DataTableRequestDTO();
            dataTableRequestDTO.setSortColumnName(sortColumn);
            dataTableRequestDTO.setSortOrder(sortOrder);
            dataTableRequestDTO.setSearch(search);

            List<StudentExaminationDTO> studentExam = studentExaminationDAO.<List<StudentExaminationEntity>>getDataForGridForStudentReport(dataTableRequestDTO)
                    .stream().map(entity -> {
                        StudentExaminationDTO dto = new StudentExaminationDTO();
                        StudentEntity studentEntity = studentDAO.getStudentEntityByUsername(entity.getSysUserEntity().getUsername());
                        dto.setId(entity.getId());
                        dto.setStudent(entity.getSysUserEntity().getUsername());
                        dto.setStudentName(entity.getSysUserEntity().getTitleEntity().getDescription() + " " + entity.getSysUserEntity().getName());
                        dto.setCompany(Optional.ofNullable(studentEntity.getCompany()).orElse(""));
                        dto.setPass("");
                        if (!ExamStatusEnum.PENDING.getCode().equals(entity.getStatusEntity().getCode())) {
                            dto.setPass(Optional.ofNullable(entity.getPass()).orElse(false) ? "Passed" : "Not Passed");
                        }
                        dto.setDateOn(commonMethod.dateTimeToString(entity.getExaminationEntity().getDateOn()));

                        dto.setExaminationCode(entity.getExaminationEntity().getCode());
                        dto.setExaminationDescription(entity.getExaminationEntity().getDescription());
                        dto.setDuration(entity.getExaminationEntity().getDuration());
                        dto.setNoQuestion(entity.getExaminationEntity().getNoQuestion());
                        dto.setStatusCode(entity.getStatusEntity().getCode());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        return dto;
                    }).collect(Collectors.toList());

            reportDTO.setReportName("studentExamination.jrxml");
            reportDTO.setDownloadName("StudentExamination");
            reportDTO.setDtoList(studentExam);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return reportDTO;
    }

    @Override
    @Transactional
    public ResponseDTO<?> generateAnswerDetailReportDetailsAndSendMail(long studentExam) {
        ReportDTO reportDTO = null;
        String code = ResponseCodeEnum.FAILED.getCode();
        String message = "Mail is not Sent";
        try {
            EmailBodyEntity emailBodyEntity = emailBodyDAO.findEmailBodyEntityByCode(EmailEnum.EFR.getCode());
            if (!Optional.ofNullable(emailBodyEntity.getEnable()).orElse(false)) {
                message = "Please Enable Exam Result Email";
                return new ResponseDTO<>(code, message);
            }
            reportDTO = getAnswerReport(studentExam);
            StudentExaminationEntity studentExaminationEntity = studentExaminationDAO.findStudentExaminationEntity(studentExam);
            DataSource attachment = reportUtil.createReportAsByteStream(reportDTO);
            String subject = emailBodyEntity.getSubject()
                    .replace("@ExamName", studentExaminationEntity.getExaminationEntity().getDescription())
                    .replace("@StudentName", studentExaminationEntity.getSysUserEntity().getTitleEntity().getDescription() + "" + studentExaminationEntity.getSysUserEntity().getName());
            String content = emailBodyEntity.getContent()
                    .replace("@ExamName", studentExaminationEntity.getExaminationEntity().getDescription())
                    .replace("@StudentName", studentExaminationEntity.getSysUserEntity().getTitleEntity().getDescription() + "" + studentExaminationEntity.getSysUserEntity().getName());
            mailSenderService.sendEmailWithPlainTextAndAttachment(studentExaminationEntity.getSysUserEntity().getUsername(), subject, content, attachment, reportDTO.getDownloadName() + ".pdf");
            code = ResponseCodeEnum.SUCCESS.getCode();
            message = "Mail is Sent";
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, message);
    }


    @Override
    @Transactional
    public DataTableResponseDTO getStudentExaminationForDataTableForAdd(DataTableRequestDTO dataTableRequestDTO) {
        List<StudentExaminationDTO> list;
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord;
        try {
            list = studentExaminationDAO.<List<StudentExaminationEntity>>getDataForGridForStudent(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
                    .stream().map(entity -> {
                        StudentExaminationDTO dto = new StudentExaminationDTO();
                        dto.setId(entity.getId());
                        dto.setStudent(entity.getSysUserEntity().getUsername());
                        dto.setStudentName(entity.getSysUserEntity().getTitleEntity().getDescription() + " " + entity.getSysUserEntity().getName());
                        dto.setExaminationCode(entity.getExaminationEntity().getCode());
                        dto.setExaminationDescription(entity.getExaminationEntity().getDescription());
                        dto.setDateOn(commonMethod.dateTimeToString(entity.getExaminationEntity().getDateOn()));
                        dto.setDuration(entity.getExaminationEntity().getDuration());
                        dto.setNoQuestion(entity.getExaminationEntity().getNoQuestion());
                        dto.setStatusCode(entity.getStatusEntity().getCode());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = studentExaminationDAO.<Long>getDataForGridForStudent(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

            responseDTO.setData(list);
            responseDTO.setRecordsTotal(numOfRecord);
            responseDTO.setRecordsFiltered(numOfRecord);
            responseDTO.setDraw(dataTableRequestDTO.getDraw());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseDTO;
    }

    @Override
    @Transactional
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForStudentExaminationAdd() {
        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {
            Date sysDate = commonMethod.getSystemDate();
            List<?> examination = examinationDAO.findAllExaminationEntities(DefaultStatusEnum.ACTIVE.getCode())
                    .stream()
                    .filter(examinationEntity ->
                            examinationEntity.getExpireOn().compareTo(sysDate) > 0
                    )
                    .map(t -> new DropDownDTO<>(t.getCode(), t.getDescription()))
                    .collect(Collectors.toList());

            List<?> student = studentDAO.findStudents(DefaultStatusEnum.ACTIVE.getCode())
                    .stream().map(t -> new DropDownDTO<>(t.getUsername(), t.getTitleEntity().getDescription() + " " + t.getName() + "-" + t.getUsername()))
                    .collect(Collectors.toList());

            map.put("student", student);
            map.put("exam", examination);

            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, map);
    }

    @Override
    @Transactional
    public ResponseDTO<?> saveStudentExamination(StudentExaminationDTO studentExaminationDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Examination  Save Failed";
        SysUserEntity sysUserEntity;
        try {
            sysUserEntity = Optional.ofNullable(sysUserDAO.getSysUserEntityByUsername(studentExaminationDTO.getStudent())).orElse(new SysUserEntity());

            StatusEntity examStatusEntity = statusDAO.findStatusEntityByCode(ExamStatusEnum.PENDING.getCode());
            ExaminationEntity examinationEntity = examinationDAO.findExaminationEntityByCode(studentExaminationDTO.getExaminationCode());

            StudentExaminationEntity studentExaminationEntity = new StudentExaminationEntity();
            studentExaminationEntity.setSysUserEntity(sysUserEntity);
            studentExaminationEntity.setExaminationEntity(examinationEntity);
            studentExaminationEntity.setStatusEntity(examStatusEntity);

            commonMethod.getPopulateEntityWhenInsert(studentExaminationEntity);

            studentExaminationDAO.saveStudentExaminationEntity(studentExaminationEntity);

            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Examination  Save Success";

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }

    @Override
    @Transactional
    public ResponseDTO<?> deleteStudentExamination(StudentExaminationDTO studentExaminationDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Examination is Removed from Student is Failed";
        try {
            StudentExaminationEntity studentExaminationEntity = studentExaminationDAO.findStudentExaminationEntity(studentExaminationDTO.getId());
            if (ExamStatusEnum.PENDING.getCode().equals(studentExaminationEntity.getStatusEntity().getCode())) {
                studentExaminationDAO.deleteStudentExaminationEntity(studentExaminationDTO.getId());
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Examination is Removed from Student is Success";
            } else {

                description = "Examination State is Not Pending";
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }

    @Override
    @Transactional
    public ResponseDTO<?> tryToCloseStudentExamination(StudentExaminationDTO studentExaminationDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Examination is stopped  Failed";
        try {
            Date systemDate = commonMethod.getSystemDate();
            StudentExaminationEntity studentExaminationEntity = studentExaminationDAO.findStudentExaminationEntity(studentExaminationDTO.getId());
            if (ExamStatusEnum.START.getCode().equals(studentExaminationEntity.getStatusEntity().getCode())) {
                if (systemDate.compareTo(studentExaminationEntity.getEndOn()) >= 0) {
                    finalStateChangeStudentExamination(studentExaminationDTO.getId());
                    code = ResponseCodeEnum.SUCCESS.getCode();
                    description = "Examination is Removed from Student is Success";
                } else {
                    description = "Examination is not able to stop now. Please try later";
                }

            } else {

                description = "Examination State is Close or Pending";
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }

    private Date getEndDateTimeForExam(Date expectedTime, Date examEndTime) {
        return (expectedTime.compareTo(examEndTime) < 1) ? expectedTime : examEndTime;
    }

    private void calculateAnswer(StudentExaminationEntity studentExaminationEntity) {

        studentExaminationQuestionAnswerDAO.findStudentExaminationQuestionAnswerEntityByStudentExamination(studentExaminationEntity.getId())
                .forEach(studentExaminationQuestionAnswerEntity -> {
                    if (Objects.nonNull(studentExaminationQuestionAnswerEntity.getQuestionAnswerEntity()))
                        studentExaminationQuestionAnswerEntity.setCorrect(studentExaminationQuestionAnswerEntity.getQuestionAnswerEntity().isCorrect());
                    else
                        studentExaminationQuestionAnswerEntity.setCorrect(false);

                    studentExaminationQuestionAnswerEntity.setCorrectQuestionAnswerEntity(Optional.ofNullable(questionAnswerDAO.findCorrectQuestionAnswerEntity(studentExaminationQuestionAnswerEntity.getQuestionEntity().getId(), DefaultStatusEnum.ACTIVE.getCode())).orElse(null));
                    commonMethod.getPopulateEntityWhenUpdate(studentExaminationQuestionAnswerEntity);
                    studentExaminationQuestionAnswerDAO.updateStudentExaminationQuestionAnswerEntity(studentExaminationQuestionAnswerEntity);
                });

    }

    private void finalStateChangeStudentExamination(Long studentExam) {
        StudentExaminationEntity studentExaminationEntity = studentExaminationDAO.findStudentExaminationEntity(studentExam);
        StatusEntity closedExamStatusEntity = statusDAO.findStatusEntityByCode(ExamStatusEnum.CLOSED.getCode());

        studentExaminationEntity.setStatusEntity(closedExamStatusEntity);
        calculateAnswer(studentExaminationEntity);

        FinalResultDTO finalResultDTO = getFinalResult(studentExam).getData();
        studentExaminationEntity.setFinalMark(Double.valueOf(finalResultDTO.getFinalMark()));
        studentExaminationEntity.setPass(studentExaminationEntity.getPassMark().compareTo(Double.valueOf(finalResultDTO.getFinalMark())) <= 0);

        commonMethod.getPopulateEntityWhenUpdate(studentExaminationEntity);
        studentExaminationDAO.updateStudentExaminationEntity(studentExaminationEntity);
    }

    private ReportDTO getAnswerReport(long studentExam) {
        ReportDTO reportDTO = new ReportDTO();
        Map<String, Object> parameters = new HashMap<>();
        reportDTO.setReportPath(CommonConstant.REPORT_PATH);

        StudentExaminationEntity studentExaminationEntity = studentExaminationDAO.findStudentExaminationEntity(studentExam);
        parameters.put("examination", studentExaminationEntity.getExaminationEntity().getDescription());
        parameters.put("category", studentExaminationEntity.getExaminationEntity().getQuestionCategoryEntity().getDescription());
        parameters.put("finalMark", studentExaminationEntity.getFinalMark().intValue());
        parameters.put("isPass", Optional.ofNullable(studentExaminationEntity.getPass()).orElse(false) ? "Geslaagd" : "Niet geslaagd");
        parameters.put("name", studentExaminationEntity.getSysUserEntity().getTitleEntity().getDescription() + " " + studentExaminationEntity.getSysUserEntity().getName());
        parameters.put("email", studentExaminationEntity.getSysUserEntity().getUsername());
        parameters.put("location", studentExaminationEntity.getExaminationEntity().getLocation());
        parameters.put("date", studentExaminationEntity.getExaminationEntity().getDateOn());
        parameters.put("type", studentExaminationEntity.getExaminationEntity().getType());
        parameters.put("finalSummery", getFinalResult(studentExam).getData());

        reportDTO.setReportParams(parameters);

        List<QuestionResultDTO> results = studentExaminationQuestionAnswerDAO.findStudentExaminationQuestionAnswerEntityByStudentExamination(studentExam)
                .stream()
                .sorted(Comparator.comparing(StudentExaminationQuestionAnswerEntity::getSeq))
                .map(studentExaminationQuestionAnswerEntity -> {
                    QuestionResultDTO questionResultDTO = new QuestionResultDTO();
                    questionResultDTO.setQuestion(studentExaminationQuestionAnswerEntity.getQuestionEntity().getDescription());
                    questionResultDTO.setAnswer(Objects.isNull(studentExaminationQuestionAnswerEntity.getQuestionAnswerEntity()) ? "Niet Beantwoord" : studentExaminationQuestionAnswerEntity.getQuestionAnswerEntity().getDescription());
                    questionResultDTO.setResult(Objects.isNull(studentExaminationQuestionAnswerEntity.getQuestionAnswerEntity()) ? "Niet Beantwoord" : (studentExaminationQuestionAnswerEntity.isCorrect() ? "JUIST" : "FOUT"));
                    questionResultDTO.setCorrectAnswer(!studentExaminationQuestionAnswerEntity.isCorrect() ? studentExaminationQuestionAnswerEntity.getCorrectQuestionAnswerEntity().getDescription() : "---");
                    questionResultDTO.setCorrect(studentExaminationQuestionAnswerEntity.isCorrect());
                    return questionResultDTO;
                }).collect(Collectors.toList());

        reportDTO.setDtoList(results);

        reportDTO.setReportName("answer.jrxml");
        reportDTO.setDownloadName(studentExaminationEntity.getSysUserEntity().getUsername() + "_" + studentExaminationEntity.getExaminationEntity().getDescription());

        return reportDTO;
    }
}
