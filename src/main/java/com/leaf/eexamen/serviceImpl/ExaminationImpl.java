package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.ExamTypeDAO;
import com.leaf.eexamen.dao.ExaminationDAO;
import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dto.ExaminationDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.ExamTypeEntity;
import com.leaf.eexamen.entity.ExaminationEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.ExaminationService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExaminationImpl implements ExaminationService {

	private ExaminationDAO examinationDAO;
	private ExamTypeDAO examTypeDAO;
	private StatusDAO statusDAO;
	private StatusCategoryDAO statusCategoryDAO;

	private CommonMethod commonMethod;



	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<ExaminationDTO> saveExamination(ExaminationDTO examinationDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Examination Save Failed";

		ExaminationEntity examinationEntity;
		try {
			examinationEntity = examinationDAO.findExaminationEntityByCode(examinationDTO.getCode());
			if (examinationEntity == null || DeleteStatusEnum.DELETE.getCode().equals(examinationEntity.getStatusEntity().getCode())) {
				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(examinationDTO.getStatusCode());
				ExamTypeEntity examTypeEntity = examTypeDAO.findExamTypeEntityByCode(examinationDTO.getExamTypeCode());

				examinationEntity = new ExaminationEntity();
				examinationEntity.setCode(examinationDTO.getCode());
				examinationEntity.setDescription(examinationDTO.getDescription());
				examinationEntity.setExamTypeEntity(examTypeEntity);
				examinationEntity.setStatusEntity(statusEntity);
				examinationEntity.setDateOn(commonMethod.stringToDateTime(examinationDTO.getDateOn()));
				examinationEntity.setLocation(examinationDTO.getLocation());
				examinationEntity.setDuration(examinationDTO.getDuration());
				examinationEntity.setEffectiveOn(commonMethod.stringToDateTime(examinationDTO.getEffectiveOn()));
				examinationEntity.setExpireOn(commonMethod.stringToDateTime(examinationDTO.getExpireOn()));

				commonMethod.getPopulateEntityWhenInsert(examinationEntity);

				examinationDAO.saveExaminationEntity(examinationEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Examination Save Successfully";
			} else {
				description = "Examination Code is Already Used ";
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
	public ResponseDTO<ExaminationDTO> updateExamination(ExaminationDTO examinationDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Examination Update Failed";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(examinationDTO.getStatusCode());
			ExamTypeEntity examTypeEntity = examTypeDAO.findExamTypeEntityByCode(examinationDTO.getExamTypeCode());

			ExaminationEntity examinationEntity = examinationDAO.findExaminationEntityByCode(examinationDTO.getCode());
			examinationEntity.setCode(examinationDTO.getCode());
			examinationEntity.setDescription(examinationDTO.getDescription());
			examinationEntity.setExamTypeEntity(examTypeEntity);
			examinationEntity.setDateOn(commonMethod.stringToDateTime(examinationDTO.getDateOn()));
			examinationEntity.setLocation(examinationDTO.getLocation());
			examinationEntity.setDuration(examinationDTO.getDuration());
			examinationEntity.setStatusEntity(statusEntity);
			examinationEntity.setEffectiveOn(commonMethod.stringToDateTime(examinationDTO.getEffectiveOn()));
			examinationEntity.setExpireOn(commonMethod.stringToDateTime(examinationDTO.getExpireOn()));

			commonMethod.getPopulateEntityWhenUpdate(examinationEntity);

			examinationDAO.updateExaminationEntity(examinationEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Examination Update Successfully";
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
	public ResponseDTO<ExaminationDTO> deleteExamination(ExaminationDTO examinationDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Examination Delete Failed";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

			ExaminationEntity examinationEntity = examinationDAO.findExaminationEntityByCode(examinationDTO.getCode());

			examinationEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(examinationEntity);

			examinationDAO.updateExaminationEntity(examinationEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Examination Delete Successfully";
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
	public ResponseDTO<ExaminationDTO> findExamination(ExaminationDTO examinationDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Examination is Not Found";
		ExaminationDTO dto = new ExaminationDTO();
		try {

			ExaminationEntity examinationEntity = examinationDAO.findExaminationEntityByCode(examinationDTO.getCode());

			if (examinationEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(examinationEntity.getStatusEntity().getCode())) {
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Examination is Found Successfully";

				dto.setCode(examinationEntity.getCode());
				dto.setDescription(examinationEntity.getDescription());
				dto.setStatusCode(examinationEntity.getStatusEntity().getCode());
				dto.setStatusDescription(examinationEntity.getStatusEntity().getDescription());
				dto.setExamTypeCode(examinationEntity.getExamTypeEntity().getCode());
				dto.setExamTypeDescription(examinationEntity.getExamTypeEntity().getDescription());
				dto.setDateOn(commonMethod.dateTimeToString(examinationEntity.getDateOn()));
				dto.setLocation(examinationEntity.getLocation());
				dto.setDuration(examinationEntity.getDuration());
				dto.setEffectiveOn(commonMethod.dateTimeToString(examinationEntity.getEffectiveOn()));
				dto.setExpireOn(commonMethod.dateTimeToString(examinationEntity.getExpireOn()));
				dto.setCreatedBy(examinationEntity.getCreatedBy());
				dto.setCreatedOn(examinationEntity.getCreatedOn());
				dto.setUpdatedBy(examinationEntity.getUpdatedBy());
				dto.setUpdatedOn(examinationEntity.getUpdatedOn());

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
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForExamination() {

		HashMap<String, Object> map = new HashMap<>();
		String code = ResponseCodeEnum.FAILED.getCode();
		try {
			List<?> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
					.getStatusEntities()
					.stream()
					.sorted(Comparator.comparing(StatusEntity::getDescription))
					.map(s -> new DropDownDTO<>(s.getCode(), s.getDescription()))
					.collect(Collectors.toList());

			List<?> examType = examTypeDAO.findAllExamTypeEntities(DefaultStatusEnum.ACTIVE.getCode())
					.stream()
					.map(s -> new DropDownDTO<>(s.getCode(), s.getDescription()))
					.collect(Collectors.toList());

			map.put("status", status);
			map.put("examType", examType);

			code = ResponseCodeEnum.SUCCESS.getCode();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseDTO<>(code, map);
	}

	@Override
	@Transactional
	public DataTableResponseDTO getExaminationsForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<ExaminationDTO> list;
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord;
		try {
			list = examinationDAO.<List<ExaminationEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						ExaminationDTO dto = new ExaminationDTO();
						dto.setCode(entity.getCode());
						dto.setDescription(entity.getDescription());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setExamTypeCode(entity.getExamTypeEntity().getDescription());
						dto.setExamTypeDescription(entity.getExamTypeEntity().getDescription());
						dto.setEffectiveOn(commonMethod.dateTimeToString(entity.getEffectiveOn()));
						dto.setExpireOn(commonMethod.dateTimeToString(entity.getExpireOn()));
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = examinationDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

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
