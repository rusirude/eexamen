package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.ExamTypeDTO;
import com.leaf.eexamen.dto.ExamTypeQuestionModelDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.ExamTypeEntity;
import com.leaf.eexamen.entity.ExamTypeQuestionModelEntity;
import com.leaf.eexamen.entity.QuestionCategoryEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.*;
import com.leaf.eexamen.service.ExamTypeService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExamTypeServiceImpl implements ExamTypeService {

	private ExamTypeDAO examTypeDAO;
	private QuestionCategoryDAO questionCategoryDAO;
	private ExamTypeQuestionModelDAO examTypeQuestionModelDAO;
	private StatusDAO statusDAO;
	private StatusCategoryDAO statusCategoryDAO;
	private CommonMethod commonMethod;


	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<ExamTypeDTO> saveExamType(ExamTypeDTO examTypeDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Exam Type Save Failed";

		ExamTypeEntity examTypeEntity;
		try {
			examTypeEntity = examTypeDAO.findExamTypeEntityByCode(examTypeDTO.getCode());
			if (examTypeEntity == null  || DeleteStatusEnum.DELETE.getCode().equals(examTypeEntity.getStatusEntity().getCode())) {
				final StatusEntity statusEntity = statusDAO.findStatusEntityByCode(examTypeDTO.getStatusCode());
				QuestionCategoryEntity questionCategoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(examTypeDTO.getQuestionCategoryCode());

				examTypeEntity = new ExamTypeEntity();
				examTypeEntity.setCode(examTypeDTO.getCode());
				examTypeEntity.setDescription(examTypeDTO.getDescription());
				examTypeEntity.setStatusEntity(statusEntity);
				examTypeEntity.setExamCategory(ExamCategoryEnum.getEnum(examTypeDTO.getExamCategoryCode()).getCode());
				examTypeEntity.setQuestionCategoryEntity(questionCategoryEntity);
				examTypeEntity.setTPassMark(examTypeDTO.getQtPassMark());
				examTypeEntity.setWPassMark(examTypeDTO.getQwPassMark());


				commonMethod.getPopulateEntityWhenInsert(examTypeEntity);

				examTypeDAO.saveExamTypeEntity(examTypeEntity);

				final ExamTypeEntity finalExamTypeEntity = examTypeEntity;


				Optional.ofNullable(examTypeDTO.getQtQuestions()).orElse(Collections.emptyList())
						.forEach(typeQuestionModelDTO -> {

							ExamTypeQuestionModelEntity examTypeQuestionModelEntity = new ExamTypeQuestionModelEntity();

							examTypeQuestionModelEntity.setExamTypeEntity(finalExamTypeEntity);
							examTypeQuestionModelEntity.setStatusEntity(statusEntity);
							examTypeQuestionModelEntity.setType(QuestionTypeEnum.THEORIM.getCode());
							examTypeQuestionModelEntity.setGroup(typeQuestionModelDTO.getGroup());
							examTypeQuestionModelEntity.setLabel(!Optional.ofNullable(typeQuestionModelDTO.getLabel()).orElse("").isEmpty()?typeQuestionModelDTO.getLabel():null);
							examTypeQuestionModelEntity.setNoQuestion(typeQuestionModelDTO.getNoQuestion());

							commonMethod.getPopulateEntityWhenInsert(examTypeQuestionModelEntity);

							examTypeQuestionModelDAO.saveExamTypeQuestionModelEntity(examTypeQuestionModelEntity);


						});

				Optional.ofNullable(examTypeDTO.getQwQuestions()).orElse(Collections.emptyList())
						.forEach(typeQuestionModelDTO -> {

							ExamTypeQuestionModelEntity examTypeQuestionModelEntity = new ExamTypeQuestionModelEntity();

							examTypeQuestionModelEntity.setExamTypeEntity(finalExamTypeEntity);
							examTypeQuestionModelEntity.setStatusEntity(statusEntity);
							examTypeQuestionModelEntity.setType(QuestionTypeEnum.WETGEVING.getCode());
							examTypeQuestionModelEntity.setGroup(typeQuestionModelDTO.getGroup());
							examTypeQuestionModelEntity.setLabel(!Optional.ofNullable(typeQuestionModelDTO.getLabel()).orElse("").isEmpty()?typeQuestionModelDTO.getLabel():null);
							examTypeQuestionModelEntity.setNoQuestion(typeQuestionModelDTO.getNoQuestion());

							commonMethod.getPopulateEntityWhenInsert(examTypeQuestionModelEntity);

							examTypeQuestionModelDAO.saveExamTypeQuestionModelEntity(examTypeQuestionModelEntity);


						});

				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "ExamType Save Successfully";
			} else {
				description = "ExamType Code is Already Used ";
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<ExamTypeDTO> updateExamType(ExamTypeDTO examTypeDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "ExamType Update Failed";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(examTypeDTO.getStatusCode());
			StatusEntity activeStatusEntity = statusDAO.findStatusEntityByCode(DefaultStatusEnum.ACTIVE.getCode());
			StatusEntity deleteStatusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());
			QuestionCategoryEntity questionCategoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(examTypeDTO.getQuestionCategoryCode());


			ExamTypeEntity examTypeEntity = examTypeDAO.findExamTypeEntityByCode(examTypeDTO.getCode());
			examTypeEntity.setCode(examTypeDTO.getCode());
			examTypeEntity.setDescription(examTypeDTO.getDescription());
			examTypeEntity.setStatusEntity(statusEntity);
			examTypeEntity.setExamCategory(ExamCategoryEnum.getEnum(examTypeDTO.getExamCategoryCode()).getCode());
			examTypeEntity.setQuestionCategoryEntity(questionCategoryEntity);
			examTypeEntity.setTPassMark(examTypeDTO.getQtPassMark());
			examTypeEntity.setWPassMark(examTypeDTO.getQwPassMark());

			commonMethod.getPopulateEntityWhenUpdate(examTypeEntity);

			examTypeDAO.updateExamTypeEntity(examTypeEntity);


			List<Long> removedQuestions = new ArrayList<>();
//			examTypeDTO.getQtQuestions()
//					.forEach(typeQuestionModelDTO -> {
//						ExamTypeQuestionModelEntity examTypeQuestionModelEntity = Optional
//								.ofNullable(examTypeQuestionModelDAO.findExamTypeQuestionModelEntity(Optional.ofNullable(typeQuestionModelDTO.getId()).orElse(0L)))
//								.orElse(new ExamTypeQuestionModelEntity());
//
//						examTypeQuestionModelEntity.setExamTypeEntity(examTypeEntity);
//						examTypeQuestionModelEntity.setType(QuestionTypeEnum.THEORIM.getCode());
//						examTypeQuestionModelEntity.setGroup(typeQuestionModelDTO.getGroup());
//						examTypeQuestionModelEntity.setLabel(typeQuestionModelDTO.getLabel());
//						examTypeQuestionModelEntity.setNoQuestion(typeQuestionModelDTO.getNoQuestion());
//						examTypeQuestionModelEntity.setStatusEntity(activeStatusEntity);
//
//						if(Objects.isNull(examTypeQuestionModelEntity.getId())){
//							commonMethod.getPopulateEntityWhenInsert(examTypeQuestionModelEntity);
//							examTypeQuestionModelDAO.saveExamTypeQuestionModelEntity(examTypeQuestionModelEntity);
//						}
//						else{
//							commonMethod.getPopulateEntityWhenUpdate(examTypeQuestionModelEntity);
//							examTypeQuestionModelDAO.updateExamTypeQuestionModelEntity(examTypeQuestionModelEntity);
//						}
//						removedQuestions.add(examTypeQuestionModelEntity.getId());
//
//					});

			Optional.ofNullable(examTypeDTO.getQtQuestions()).orElse(Collections.emptyList())
					.forEach(typeQuestionModelDTO -> {
						ExamTypeQuestionModelEntity examTypeQuestionModelEntity = Optional
								.ofNullable(examTypeQuestionModelDAO.findExamTypeQuestionModelEntity(Optional.ofNullable(typeQuestionModelDTO.getId()).orElse(0L)))
								.orElse(new ExamTypeQuestionModelEntity());

						examTypeQuestionModelEntity.setExamTypeEntity(examTypeEntity);
						examTypeQuestionModelEntity.setType(QuestionTypeEnum.THEORIM.getCode());
						examTypeQuestionModelEntity.setGroup(typeQuestionModelDTO.getGroup());
						examTypeQuestionModelEntity.setLabel(!Optional.ofNullable(typeQuestionModelDTO.getLabel()).orElse("").isEmpty()?typeQuestionModelDTO.getLabel():null);
						examTypeQuestionModelEntity.setNoQuestion(typeQuestionModelDTO.getNoQuestion());
						examTypeQuestionModelEntity.setStatusEntity(activeStatusEntity);

						if(Objects.isNull(examTypeQuestionModelEntity.getId())){
							commonMethod.getPopulateEntityWhenInsert(examTypeQuestionModelEntity);
							examTypeQuestionModelDAO.saveExamTypeQuestionModelEntity(examTypeQuestionModelEntity);
						}
						else{
							commonMethod.getPopulateEntityWhenUpdate(examTypeQuestionModelEntity);
							examTypeQuestionModelDAO.updateExamTypeQuestionModelEntity(examTypeQuestionModelEntity);
						}
						removedQuestions.add(examTypeQuestionModelEntity.getId());

					});

			Optional.ofNullable(examTypeDTO.getQwQuestions()).orElse(Collections.emptyList())
					.forEach(typeQuestionModelDTO -> {
						ExamTypeQuestionModelEntity examTypeQuestionModelEntity = Optional
								.ofNullable(examTypeQuestionModelDAO.findExamTypeQuestionModelEntity(Optional.ofNullable(typeQuestionModelDTO.getId()).orElse(0L)))
								.orElse(new ExamTypeQuestionModelEntity());

						examTypeQuestionModelEntity.setExamTypeEntity(examTypeEntity);
						examTypeQuestionModelEntity.setType(QuestionTypeEnum.WETGEVING.getCode());
						examTypeQuestionModelEntity.setGroup(typeQuestionModelDTO.getGroup());
						examTypeQuestionModelEntity.setLabel(!Optional.ofNullable(typeQuestionModelDTO.getLabel()).orElse("").isEmpty()?typeQuestionModelDTO.getLabel():null);
						examTypeQuestionModelEntity.setNoQuestion(typeQuestionModelDTO.getNoQuestion());
						examTypeQuestionModelEntity.setStatusEntity(activeStatusEntity);

						if(Objects.isNull(examTypeQuestionModelEntity.getId())){
							commonMethod.getPopulateEntityWhenInsert(examTypeQuestionModelEntity);
							examTypeQuestionModelDAO.saveExamTypeQuestionModelEntity(examTypeQuestionModelEntity);
						}
						else{
							commonMethod.getPopulateEntityWhenUpdate(examTypeQuestionModelEntity);
							examTypeQuestionModelDAO.updateExamTypeQuestionModelEntity(examTypeQuestionModelEntity);
						}
						removedQuestions.add(examTypeQuestionModelEntity.getId());

					});

			if (!removedQuestions.isEmpty()){
				examTypeQuestionModelDAO.findAllExamTypeQuestionModelEntitiesByExamTypeAndNotInIds(examTypeEntity.getId(), removedQuestions)
						.forEach(examTypeQuestionModelEntity -> {
							examTypeQuestionModelEntity.setStatusEntity(deleteStatusEntity);
							commonMethod.getPopulateEntityWhenUpdate(examTypeQuestionModelEntity);
							examTypeQuestionModelDAO.updateExamTypeQuestionModelEntity(examTypeQuestionModelEntity);
						});
			}





			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "ExamType Update Successfully";
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<ExamTypeDTO> deleteExamType(ExamTypeDTO examTypeDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "ExamType Delete Failed";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

			ExamTypeEntity examTypeEntity = examTypeDAO.findExamTypeEntityByCode(examTypeDTO.getCode());

			examTypeEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(examTypeEntity);

			examTypeDAO.updateExamTypeEntity(examTypeEntity);


			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "ExamType Delete Successfully";
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<ExamTypeDTO> findExamType(ExamTypeDTO examTypeDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "ExamType is Not Found";
		ExamTypeDTO dto = new ExamTypeDTO();
		try {

			ExamTypeEntity examTypeEntity = examTypeDAO.findExamTypeEntityByCode(examTypeDTO.getCode());

			if (examTypeEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(examTypeEntity.getStatusEntity().getCode())) {
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "ExamType is Found Successfully";

				dto.setCode(examTypeEntity.getCode());
				dto.setDescription(examTypeEntity.getDescription());
				dto.setStatusCode(examTypeEntity.getStatusEntity().getCode());
				dto.setStatusDescription(examTypeEntity.getStatusEntity().getDescription());
				dto.setExamCategoryCode(examTypeEntity.getExamCategory());
				dto.setExamCategoryDescription(ExamCategoryEnum.getEnum(examTypeEntity.getExamCategory()).getDescription());
				dto.setQuestionCategoryCode(examTypeEntity.getQuestionCategoryEntity().getCode());
				dto.setQuestionCategoryDescription(examTypeEntity.getQuestionCategoryEntity().getDescription());
				dto.setQtPassMark(examTypeEntity.getTPassMark());
				dto.setQwPassMark(examTypeEntity.getWPassMark());
				dto.setCreatedBy(examTypeEntity.getCreatedBy());
				dto.setCreatedOn(examTypeEntity.getCreatedOn());
				dto.setUpdatedBy(examTypeEntity.getUpdatedBy());
				dto.setUpdatedOn(examTypeEntity.getUpdatedOn());



				List<ExamTypeQuestionModelDTO> tExamTypeQuestionModelDTOs = examTypeQuestionModelDAO.findAllExamTypeQuestionModelEntitiesByExamTypeAndQuestionType(examTypeEntity.getId(), QuestionTypeEnum.THEORIM.getCode(),DefaultStatusEnum.ACTIVE.getCode())
						.stream()
						.map(examTypeQuestionModelEntity -> {
							ExamTypeQuestionModelDTO examTypeQuestionModelDTO = new ExamTypeQuestionModelDTO();
							examTypeQuestionModelDTO.setId(examTypeQuestionModelEntity.getId());
							examTypeQuestionModelDTO.setTypeCode(QuestionTypeEnum.THEORIM.getCode());
							examTypeQuestionModelDTO.setTypeDescription(QuestionTypeEnum.THEORIM.getDescription());
							examTypeQuestionModelDTO.setGroup(examTypeQuestionModelEntity.getGroup());
							examTypeQuestionModelDTO.setLabel(Optional.ofNullable(examTypeQuestionModelEntity.getLabel()).orElse(""));
							examTypeQuestionModelDTO.setNoQuestion(examTypeQuestionModelEntity.getNoQuestion());
							examTypeQuestionModelDTO.setStatusCode(examTypeQuestionModelEntity.getStatusEntity().getCode());
							examTypeQuestionModelDTO.setStatusDescription(examTypeQuestionModelEntity.getStatusEntity().getDescription());
							return examTypeQuestionModelDTO;
						})
						.collect(Collectors.toList());

				dto.setQtQuestions(tExamTypeQuestionModelDTOs);


				List<ExamTypeQuestionModelDTO> wExamTypeQuestionModelDTOs = examTypeQuestionModelDAO.findAllExamTypeQuestionModelEntitiesByExamTypeAndQuestionType(examTypeEntity.getId(), QuestionTypeEnum.WETGEVING.getCode(),DefaultStatusEnum.ACTIVE.getCode())
						.stream()
						.map(examTypeQuestionModelEntity -> {
							ExamTypeQuestionModelDTO examTypeQuestionModelDTO = new ExamTypeQuestionModelDTO();
							examTypeQuestionModelDTO.setId(examTypeQuestionModelEntity.getId());
							examTypeQuestionModelDTO.setTypeCode(QuestionTypeEnum.WETGEVING.getCode());
							examTypeQuestionModelDTO.setTypeDescription(QuestionTypeEnum.WETGEVING.getDescription());
							examTypeQuestionModelDTO.setGroup(examTypeQuestionModelEntity.getGroup());
							examTypeQuestionModelDTO.setLabel(Optional.ofNullable(examTypeQuestionModelEntity.getLabel()).orElse(""));
							examTypeQuestionModelDTO.setNoQuestion(examTypeQuestionModelEntity.getNoQuestion());
							examTypeQuestionModelDTO.setStatusCode(examTypeQuestionModelEntity.getStatusEntity().getCode());
							examTypeQuestionModelDTO.setStatusDescription(examTypeQuestionModelEntity.getStatusEntity().getDescription());
							return examTypeQuestionModelDTO;
						})
						.collect(Collectors.toList());

				dto.setQwQuestions(wExamTypeQuestionModelDTOs);



			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description, dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForExamType() {

		HashMap<String, Object> map = new HashMap<>();
		String code = ResponseCodeEnum.FAILED.getCode();
		try {
			List<?> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
					.getStatusEntities().stream()
					.sorted(Comparator.comparing(StatusEntity::getDescription)).map(s -> new DropDownDTO(s.getCode(), s.getDescription()))
					.collect(Collectors.toList());

			List<?> questionCategory = questionCategoryDAO.findAllQuestionCategoryEntities(DefaultStatusEnum.ACTIVE.getCode())
					.stream().map(s -> new DropDownDTO<>(s.getCode(), s.getDescription()))
					.collect(Collectors.toList());

			List<?> examCategory = Stream.of(ExamCategoryEnum.values())
					.map(examCategoryEnum -> new DropDownDTO<>(examCategoryEnum.getCode(),examCategoryEnum.getDescription()))
					.collect(Collectors.toList());

			map.put("status", status);
			map.put("questionCategory", questionCategory);
			map.put("examCategory", examCategory);

			code = ResponseCodeEnum.SUCCESS.getCode();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, map);
	}

	@Override
	@Transactional
	public DataTableResponseDTO getExamTypesForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<ExamTypeDTO> list;
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord;
		try {
			list = examTypeDAO.<List<ExamTypeEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						ExamTypeDTO dto = new ExamTypeDTO();
						dto.setCode(entity.getCode());
						dto.setDescription(entity.getDescription());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = examTypeDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

			responseDTO.setData(list);
			responseDTO.setRecordsTotal(numOfRecord);
			responseDTO.setRecordsFiltered(numOfRecord);
			responseDTO.setDraw(dataTableRequestDTO.getDraw());

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return responseDTO;
	}

}
