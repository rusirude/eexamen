package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.QuestionCategoryDAO;
import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dto.QuestionCategoryDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.QuestionCategoryEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.QuestionCategoryService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionCategoryServiceImpl implements QuestionCategoryService {

	private QuestionCategoryDAO questionCategoryDAO;
	private StatusDAO statusDAO;
	private StatusCategoryDAO statusCategoryDAO;

	private CommonMethod commonMethod;

	@Autowired
	public QuestionCategoryServiceImpl(QuestionCategoryDAO questionCategoryDAO, StatusDAO statusDAO, StatusCategoryDAO statusCategoryDAO,
									   CommonMethod commonMethod) {
		this.questionCategoryDAO = questionCategoryDAO;
		this.statusDAO = statusDAO;
		this.statusCategoryDAO = statusCategoryDAO;
		this.commonMethod = commonMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<QuestionCategoryDTO> saveQuestionCategory(QuestionCategoryDTO questionCategoryDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "QuestionCategory Save Faield";

		QuestionCategoryEntity questionCategoryEntity;
		try {
			questionCategoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(questionCategoryDTO.getCode());
			if (questionCategoryEntity == null) {
				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(questionCategoryDTO.getStatusCode());

				questionCategoryEntity = new QuestionCategoryEntity();
				questionCategoryEntity.setCode(questionCategoryDTO.getCode());
				questionCategoryEntity.setDescription(questionCategoryDTO.getDescription());
				questionCategoryEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(questionCategoryEntity);

				questionCategoryDAO.saveQuestionCategoryEntity(questionCategoryEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "QuestionCategory Save Successfully";
			} else if (DeleteStatusEnum.DELETE.getCode().equals(questionCategoryEntity.getStatusEntity().getCode())) {

				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(questionCategoryDTO.getStatusCode());

				questionCategoryEntity.setCode(questionCategoryDTO.getCode());
				questionCategoryEntity.setDescription(questionCategoryDTO.getDescription());
				questionCategoryEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(questionCategoryEntity);

				questionCategoryDAO.updateQuestionCategoryEntity(questionCategoryEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "QuestionCategory Save Successfully";
			} else {
				description = "QuestionCategory Code is Already Used ";
			}

		} catch (Exception e) {
			System.err.println("QuestionCategory Save Issue");
		}
		return new ResponseDTO<QuestionCategoryDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<QuestionCategoryDTO> updateQuestionCategory(QuestionCategoryDTO questionCategoryDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "QuestionCategory Update Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(questionCategoryDTO.getStatusCode());

			QuestionCategoryEntity questionCategoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(questionCategoryDTO.getCode());
			questionCategoryEntity.setCode(questionCategoryDTO.getCode());
			questionCategoryEntity.setDescription(questionCategoryDTO.getDescription());
			questionCategoryEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(questionCategoryEntity);

			questionCategoryDAO.updateQuestionCategoryEntity(questionCategoryEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "QuestionCategory Update Successfully";
		} catch (Exception e) {
			System.err.println("QuestionCategory Update Issue");
		}
		return new ResponseDTO<QuestionCategoryDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<QuestionCategoryDTO> deleteQuestionCategory(QuestionCategoryDTO questionCategoryDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Question Category Delete Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

			QuestionCategoryEntity questionCategoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(questionCategoryDTO.getCode());

			questionCategoryEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(questionCategoryEntity);

			questionCategoryDAO.updateQuestionCategoryEntity(questionCategoryEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "QuestionCategory Delete Successfully";
		} catch (Exception e) {
			System.err.println("QuestionCategory Delete Issue");
		}
		return new ResponseDTO<QuestionCategoryDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<QuestionCategoryDTO> findQuestionCategory(QuestionCategoryDTO questionCategoryDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "QuestionCategory is Not Found";
		QuestionCategoryDTO dto = new QuestionCategoryDTO();
		try {

			QuestionCategoryEntity questionCategoryEntity = questionCategoryDAO.findQuestionCategoryEntityByCode(questionCategoryDTO.getCode());

			if (questionCategoryEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(questionCategoryEntity.getStatusEntity().getCode())) {
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "QuestionCategory is Found Successfully";

				dto.setCode(questionCategoryEntity.getCode());
				dto.setDescription(questionCategoryEntity.getDescription());
				dto.setStatusCode(questionCategoryEntity.getStatusEntity().getCode());
				dto.setStatusDescription(questionCategoryEntity.getStatusEntity().getDescription());
				dto.setCreatedBy(questionCategoryEntity.getCreatedBy());
				dto.setCreatedOn(questionCategoryEntity.getCreatedOn());
				dto.setUpdatedBy(questionCategoryEntity.getUpdatedBy());
				dto.setUpdatedOn(questionCategoryEntity.getUpdatedOn());

			}

		} catch (Exception e) {
			System.err.println("QuestionCategory Find Issue");
		}
		return new ResponseDTO<QuestionCategoryDTO>(code, description, dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForQuestionCategory() {

		HashMap<String, Object> map = new HashMap<>();
		String code = ResponseCodeEnum.FAILED.getCode();
		try {
			List<DropDownDTO> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
					.getStatusEntities().stream()
					.sorted(Comparator.comparing(StatusEntity::getDescription)).map(s -> new DropDownDTO(s.getCode(), s.getDescription()))
					.collect(Collectors.toList());

			map.put("status", status);

			code = ResponseCodeEnum.SUCCESS.getCode();
		} catch (Exception e) {
			System.err.println("QuestionCategory Ref Data Issue");
		}
		return new ResponseDTO<HashMap<String, Object>>(code, map);
	}

	@Override
	@Transactional
	public DataTableResponseDTO getQuestionCategoriesForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<QuestionCategoryDTO> list = new ArrayList<>();
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord = Long.valueOf(0);
		try {
			list = questionCategoryDAO.<List<QuestionCategoryEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						QuestionCategoryDTO dto = new QuestionCategoryDTO();
						dto.setCode(entity.getCode());
						dto.setDescription(entity.getDescription());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = questionCategoryDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

			responseDTO.setData(list);
			responseDTO.setRecordsTotal(numOfRecord);
			responseDTO.setRecordsFiltered(numOfRecord);
			responseDTO.setDraw(dataTableRequestDTO.getDraw());

		} catch (Exception e) {
		}

		return responseDTO;
	}

}
