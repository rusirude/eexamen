package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.QuestionAnswerDTO;
import com.leaf.eexamen.dto.QuestionCategoryDTO;
import com.leaf.eexamen.dto.QuestionDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.*;
import com.leaf.eexamen.enums.*;
import com.leaf.eexamen.service.QuestionService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionServiceImpl implements QuestionService {

	private QuestionDAO questionDAO;
	private QuestionCategoryDAO questionCategoryDAO;
	private QuestionAnswerDAO questionAnswerDAO;
	private QuestionQuestionCategoryDAO questionQuestionCategoryDAO;
	private StatusDAO statusDAO;
	private StatusCategoryDAO statusCategoryDAO;
	private CommonMethod commonMethod;


	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<QuestionDTO> saveQuestion(QuestionDTO questionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Question Save Failed";

		QuestionEntity questionEntity;
		try {
			questionEntity = questionDAO.findQuestionEntityByCode(questionDTO.getCode());
			if (questionEntity == null  || DeleteStatusEnum.DELETE.getCode().equals(questionEntity.getStatusEntity().getCode())) {
				final StatusEntity statusEntity = statusDAO.findStatusEntityByCode(questionDTO.getStatusCode());

				questionEntity = new QuestionEntity();
				questionEntity.setCode(questionDTO.getCode());
				questionEntity.setType(questionDTO.getType());
				questionEntity.setGroup(questionDTO.getGroup());
				questionEntity.setLabel(!Optional.ofNullable(questionDTO.getLabel()).orElse("").isEmpty()?questionDTO.getLabel():null);
				questionEntity.setDescription(questionDTO.getDescription());
				questionEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(questionEntity);

				questionDAO.saveQuestionEntity(questionEntity);

				final QuestionEntity finalQuestionEntity = questionEntity;
				questionDTO.getQuestionCategories()
						.forEach(questionCategoryDTO -> {
							QuestionCategoryEntity categoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(questionCategoryDTO.getCode());

							QuestionQuestionCategoryEntity questionQuestionCategoryEntity = new QuestionQuestionCategoryEntity();
							QuestionQuestionCategoryEntityId questionQuestionCategoryEntityId = new QuestionQuestionCategoryEntityId();

							questionQuestionCategoryEntityId.setQuestion(finalQuestionEntity.getId());
							questionQuestionCategoryEntityId.setQuestionCategory(categoryEntity.getId());

							questionQuestionCategoryEntity.setQuestionQuestionCategoryEntityId(questionQuestionCategoryEntityId);
							questionQuestionCategoryEntity.setQuestionEntity(finalQuestionEntity);
							questionQuestionCategoryEntity.setQuestionCategoryEntity(categoryEntity);

							questionQuestionCategoryDAO.saveQuestionQuestionCategoryEntity(questionQuestionCategoryEntity);


						});

				questionDTO.getQuestionAnswers()
						.forEach(questionAnswerDTO -> {

							QuestionAnswerEntity questionAnswerEntity = new QuestionAnswerEntity();

							questionAnswerEntity.setQuestionEntity(finalQuestionEntity);
							questionAnswerEntity.setStatusEntity(statusEntity);
							questionAnswerEntity.setDescription(questionAnswerDTO.getDescription());
							questionAnswerEntity.setCorrect(questionAnswerDTO.isCorrect());
							questionAnswerEntity.setPosition(questionAnswerDTO.getPosition());

							commonMethod.getPopulateEntityWhenInsert(questionAnswerEntity);

							questionAnswerDAO.saveQuestionAnswerEntity(questionAnswerEntity);


						});

				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Question Save Successfully";
			} else {
				description = "Question Code is Already Used ";
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
	public ResponseDTO<QuestionDTO> updateQuestion(QuestionDTO questionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Question Update Failed";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(questionDTO.getStatusCode());
			StatusEntity activeStatusEntity = statusDAO.findStatusEntityByCode(DefaultStatusEnum.ACTIVE.getCode());
			StatusEntity deleteStatusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

			QuestionEntity questionEntity = questionDAO.findQuestionEntityByCode(questionDTO.getCode());
			questionEntity.setCode(questionDTO.getCode());
			questionEntity.setDescription(questionDTO.getDescription());
			questionEntity.setType(questionDTO.getType());
			questionEntity.setGroup(questionDTO.getGroup());
			questionEntity.setLabel(!Optional.ofNullable(questionDTO.getLabel()).orElse("").isEmpty()?questionDTO.getLabel():null);
			questionEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(questionEntity);

			questionDAO.updateQuestionEntity(questionEntity);


			questionQuestionCategoryDAO.deleteQuestionQuestionCategoryEntityByQuestion(questionEntity.getId());
			questionDTO.getQuestionCategories()
					.forEach(questionCategoryDTO -> {
						QuestionCategoryEntity categoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(questionCategoryDTO.getCode());

						QuestionQuestionCategoryEntity questionQuestionCategoryEntity = new QuestionQuestionCategoryEntity();
						QuestionQuestionCategoryEntityId questionQuestionCategoryEntityId = new QuestionQuestionCategoryEntityId();


						questionQuestionCategoryEntityId.setQuestion(questionEntity.getId());
						questionQuestionCategoryEntityId.setQuestionCategory(categoryEntity.getId());

						questionQuestionCategoryEntity.setQuestionQuestionCategoryEntityId(questionQuestionCategoryEntityId);
						questionQuestionCategoryEntity.setQuestionEntity(questionEntity);
						questionQuestionCategoryEntity.setQuestionCategoryEntity(categoryEntity);

						questionQuestionCategoryDAO.saveQuestionQuestionCategoryEntity(questionQuestionCategoryEntity);


					});

			List<Long> answers = new ArrayList<>();
			questionDTO.getQuestionAnswers()
					.forEach(questionAnswerDTO -> {
						QuestionAnswerEntity questionAnswerEntity = Optional
								.ofNullable(questionAnswerDAO.findQuestionAnswerEntity(Optional.ofNullable(questionAnswerDTO.getId()).orElse(0L)))
								.orElse(new QuestionAnswerEntity());

						questionAnswerEntity.setQuestionEntity(questionEntity);
						questionAnswerEntity.setPosition(questionAnswerDTO.getPosition());
						questionAnswerEntity.setStatusEntity(activeStatusEntity);
						questionAnswerEntity.setDescription(questionAnswerDTO.getDescription());
						questionAnswerEntity.setCorrect(questionAnswerDTO.isCorrect());

						if(Objects.isNull(questionAnswerEntity.getId())){
							commonMethod.getPopulateEntityWhenInsert(questionAnswerEntity);
							questionAnswerDAO.saveQuestionAnswerEntity(questionAnswerEntity);
						}
						else{
							commonMethod.getPopulateEntityWhenUpdate(questionAnswerEntity);
							questionAnswerDAO.updateQuestionAnswerEntity(questionAnswerEntity);
						}
						answers.add(questionAnswerEntity.getId());

					});

			if(!answers.isEmpty()){
				questionAnswerDAO.findAllQuestionAnswerEntitiesByQuestionAndNotInAnswers(questionEntity.getId(),answers)
						.forEach(questionAnswerEntity -> {
							questionAnswerEntity.setStatusEntity(deleteStatusEntity);
							commonMethod.getPopulateEntityWhenUpdate(questionAnswerEntity);
							questionAnswerDAO.updateQuestionAnswerEntity(questionAnswerEntity);
						});
			}





			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Question Update Successfully";
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
	public ResponseDTO<QuestionDTO> deleteQuestion(QuestionDTO questionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Question Delete Failed";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

			QuestionEntity questionEntity = questionDAO.findQuestionEntityByCode(questionDTO.getCode());

			questionEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(questionEntity);

			questionDAO.updateQuestionEntity(questionEntity);

			questionQuestionCategoryDAO.deleteQuestionQuestionCategoryEntityByQuestion(questionEntity.getId());


			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Question Delete Successfully";
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
	public ResponseDTO<QuestionDTO> findQuestion(QuestionDTO questionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Question is Not Found";
		QuestionDTO dto = new QuestionDTO();
		try {

			QuestionEntity questionEntity = questionDAO.findQuestionEntityByCode(questionDTO.getCode());

			if (questionEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(questionEntity.getStatusEntity().getCode())) {
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Question is Found Successfully";

				dto.setCode(questionEntity.getCode());
				dto.setDescription(questionEntity.getDescription());
				dto.setStatusCode(questionEntity.getStatusEntity().getCode());
				dto.setType(questionEntity.getType());
				dto.setGroup(questionEntity.getGroup());
				dto.setLabel(questionEntity.getLabel());
				dto.setStatusDescription(questionEntity.getStatusEntity().getDescription());
				dto.setCreatedBy(questionEntity.getCreatedBy());
				dto.setCreatedOn(questionEntity.getCreatedOn());
				dto.setUpdatedBy(questionEntity.getUpdatedBy());
				dto.setUpdatedOn(questionEntity.getUpdatedOn());

				List<QuestionCategoryDTO> questionCategoryDTOs = questionQuestionCategoryDAO.getQuestionQuestionCategoryEntity(questionEntity.getId())
						.stream()
						.map(questionQuestionCategoryEntity -> {
							QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
							questionCategoryDTO.setCode(questionQuestionCategoryEntity.getQuestionCategoryEntity().getCode());
							questionCategoryDTO.setDescription(questionQuestionCategoryEntity.getQuestionCategoryEntity().getDescription());
							return questionCategoryDTO;
						})
						.collect(Collectors.toList());

				dto.setQuestionCategories(questionCategoryDTOs);

				List<QuestionAnswerDTO> questionAnswerDTOs = questionAnswerDAO.findAllQuestionAnswerEntitiesByQuestion(questionEntity.getId(),DefaultStatusEnum.ACTIVE.getCode())
						.stream()
						.map(questionAnswerEntity -> {
							QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
							questionAnswerDTO.setId(questionAnswerEntity.getId());
							questionAnswerDTO.setDescription(questionAnswerEntity.getDescription());
							questionAnswerDTO.setCorrect(questionAnswerEntity.isCorrect());
							return questionAnswerDTO;
						})
						.collect(Collectors.toList());

				dto.setQuestionAnswers(questionAnswerDTOs);



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
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForQuestion() {

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

			map.put("status", status);
			map.put("questionCategory", questionCategory);

			code = ResponseCodeEnum.SUCCESS.getCode();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, map);
	}

	@Override
	@Transactional
	public DataTableResponseDTO getQuestionsForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<QuestionDTO> list;
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord;
		try {
			list = questionDAO.<List<QuestionEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						QuestionDTO dto = new QuestionDTO();
						dto.setCode(entity.getCode());
						dto.setDescription(entity.getDescription());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = questionDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

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
	public ResponseDTO<?> uploadExcel(MultipartFile file) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Upload Failed";
		try {




			Workbook workbook = new HSSFWorkbook(file.getInputStream());

			Sheet sheet = workbook.getSheet("sheet1");
			Iterator<Row> rows = sheet.iterator();

			int rowNumber = 0;
			while (rows.hasNext() && rowNumber< 478) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				QuestionDTO question = new QuestionDTO();
				List<QuestionAnswerDTO> questionAnswerDTOs = new ArrayList<>();
				List<QuestionCategoryDTO> questionCategories  = new ArrayList<>();
				int position = 1;
				int cellIdx = 0;
				while (cellsInRow.hasNext() && cellIdx <21) {
					Cell currentCell = cellsInRow.next();
					if(cellIdx == 0){
						question.setCode("Q"+rowNumber);
						question.setStatusCode(DefaultStatusEnum.ACTIVE.getCode());
					}
					else if(cellIdx==1)
						question.setDescription(currentCell.getStringCellValue());
					else if(1 < cellIdx && cellIdx < 6){
						currentCell.setCellType(CellType.STRING);
						QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
						questionAnswerDTO.setPosition(position++);
						if(CellType.STRING.equals(currentCell.getCellType()))
							questionAnswerDTO.setDescription(currentCell.getRichStringCellValue().getString());
						else
							questionAnswerDTO.setDescription(String.valueOf(currentCell.getNumericCellValue()));
						questionAnswerDTO.setCorrect(workbook.getFontAt(currentCell.getCellStyle().getFontIndexAsInt()).getBold());
						questionAnswerDTOs.add(questionAnswerDTO);
					}
					else if(cellIdx == 6){
						question.setType(Objects.equals(currentCell.getStringCellValue().trim(), "X") ? QuestionTypeEnum.THEORIM.getCode():QuestionTypeEnum.WETGEVING.getCode());
					}
					else if(cellIdx == 8 && Objects.equals(currentCell.getStringCellValue().trim(), "X")){
						QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
						questionCategoryDTO.setCode("CAT1");
						questionCategories.add(questionCategoryDTO);
					}
					else if(cellIdx == 9 && Objects.equals(currentCell.getStringCellValue().trim(), "X")){
						QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
						questionCategoryDTO.setCode("CAT2");
						questionCategories.add(questionCategoryDTO);
					}
					else if(cellIdx == 10 && Objects.equals(currentCell.getStringCellValue().trim(), "X")){
						QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
						questionCategoryDTO.setCode("CAT3");
						questionCategories.add(questionCategoryDTO);
					}
					else if(cellIdx == 11 && Objects.equals(currentCell.getStringCellValue().trim(), "X")){
						QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
						questionCategoryDTO.setCode("CAT4");
						questionCategories.add(questionCategoryDTO);
					}
					else if(cellIdx == 12)
						question.setGroup(String.valueOf(currentCell.getNumericCellValue()).substring(0, String.valueOf(currentCell.getNumericCellValue()).length() - 2));

					else if((12 < cellIdx && cellIdx < 17) && Objects.equals(currentCell.getStringCellValue().trim(), "X")){
						question.setLabel("A");
					}

					else if((16 < cellIdx && cellIdx < 21) && Objects.equals(currentCell.getStringCellValue().trim(), "X")){
						question.setLabel("B");
					}


					cellIdx++;
				}
				question.setQuestionAnswers(questionAnswerDTOs);
				question.setQuestionCategories(questionCategories);
				saveQuestion(question);
				System.out.println(rowNumber+"-"+ question);
				rowNumber++;

			}

			workbook.close();

			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Upload Successfully";
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, description);
	}

}
