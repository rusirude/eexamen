package com.leaf.eexamen.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leaf.eexamen.dao.SectionDAO;
import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dto.SectionDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.SectionEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.SectionService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;

@Service
public class SectionServiceImpl implements SectionService {

	private SectionDAO sectionDAO;
	private StatusDAO statusDAO;
	private StatusCategoryDAO statusCategoryDAO;

	private CommonMethod commonMethod;

	@Autowired
	public SectionServiceImpl(SectionDAO sectionDAO, StatusDAO statusDAO, StatusCategoryDAO statusCategoryDAO,
			CommonMethod commonMethod) {
		this.sectionDAO = sectionDAO;
		this.statusDAO = statusDAO;
		this.statusCategoryDAO = statusCategoryDAO;
		this.commonMethod = commonMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SectionDTO> saveSection(SectionDTO sectionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Section Save Faield";

		SectionEntity sectionEntity;
		try {
			sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());
			if (sectionEntity == null) {
				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sectionDTO.getStatusCode());

				sectionEntity = new SectionEntity();
				sectionEntity.setCode(sectionDTO.getCode());
				sectionEntity.setDescription(sectionDTO.getDescription());
				sectionEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(sectionEntity);

				sectionDAO.saveSectionEntity(sectionEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Section Save Successfully";
			} else if (DeleteStatusEnum.DELETE.getCode().equals(sectionEntity.getStatusEntity().getCode())) {

				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sectionDTO.getStatusCode());

				sectionEntity.setCode(sectionDTO.getCode());
				sectionEntity.setDescription(sectionDTO.getDescription());
				sectionEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(sectionEntity);

				sectionDAO.updateSectionEntity(sectionEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Section Save Successfully";
			} else {
				description = "Section Code is Already Used ";
			}

		} catch (Exception e) {
			System.err.println("Section Save Issue");
		}
		return new ResponseDTO<SectionDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SectionDTO> updateSection(SectionDTO sectionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Section Update Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sectionDTO.getStatusCode());

			SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());
			sectionEntity.setCode(sectionDTO.getCode());
			sectionEntity.setDescription(sectionDTO.getDescription());
			sectionEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(sectionEntity);

			sectionDAO.updateSectionEntity(sectionEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Section Update Successfully";
		} catch (Exception e) {
			System.err.println("Section Update Issue");
		}
		return new ResponseDTO<SectionDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SectionDTO> deleteSection(SectionDTO sectionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Section Delete Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

			SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());

			sectionEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(sectionEntity);

			sectionDAO.updateSectionEntity(sectionEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "Section Delete Successfully";
		} catch (Exception e) {
			System.err.println("Section Delete Issue");
		}
		return new ResponseDTO<SectionDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SectionDTO> findSection(SectionDTO sectionDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "Section is Not Found";
		SectionDTO dto = new SectionDTO();
		try {

			SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());

			if (sectionEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(sectionEntity.getStatusEntity().getCode())) {
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "Section is Found Successfully";

				dto.setCode(sectionEntity.getCode());
				dto.setDescription(sectionEntity.getDescription());
				dto.setStatusCode(sectionEntity.getStatusEntity().getCode());
				dto.setStatusDescription(sectionEntity.getStatusEntity().getDescription());
				dto.setCreatedBy(sectionEntity.getCreatedBy());
				dto.setCreatedOn(sectionEntity.getCreatedOn());
				dto.setUpdatedBy(sectionEntity.getUpdatedBy());
				dto.setUpdatedOn(sectionEntity.getUpdatedOn());

			}

		} catch (Exception e) {
			System.err.println("Section Find Issue");
		}
		return new ResponseDTO<SectionDTO>(code, description, dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForSection() {

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
			System.err.println("Section Ref Data Issue");
		}
		return new ResponseDTO<HashMap<String, Object>>(code, map);
	}

	@Override
	@Transactional
	public DataTableResponseDTO getSectionsForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<SectionDTO> list = new ArrayList<>();
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord = Long.valueOf(0);
		try {
			list = sectionDAO.<List<SectionEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						SectionDTO dto = new SectionDTO();
						dto.setCode(entity.getCode());
						dto.setDescription(entity.getDescription());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = sectionDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

			responseDTO.setData(list);
			responseDTO.setRecordsTotal(numOfRecord);
			responseDTO.setRecordsFiltered(numOfRecord);
			responseDTO.setDraw(dataTableRequestDTO.getDraw());

		} catch (Exception e) {
		}

		return responseDTO;
	}

}
