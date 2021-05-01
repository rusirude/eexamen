package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dao.CityDAO;
import com.leaf.eexamen.dto.CityDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.CityEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.CityService;
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
public class CityServiceImpl implements CityService {

	private CityDAO cityDAO;
	private StatusDAO statusDAO;
	private StatusCategoryDAO statusCategoryDAO;

	private CommonMethod commonMethod;

	@Autowired
	public CityServiceImpl(CityDAO cityDAO, StatusDAO statusDAO, StatusCategoryDAO statusCategoryDAO,
						   CommonMethod commonMethod) {
		this.cityDAO = cityDAO;
		this.statusDAO = statusDAO;
		this.statusCategoryDAO = statusCategoryDAO;
		this.commonMethod = commonMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<CityDTO> saveCity(CityDTO cityDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "City Save Faield";

		CityEntity cityEntity;
		try {
			cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());
			if (cityEntity == null) {
				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(cityDTO.getStatusCode());

				cityEntity = new CityEntity();
				cityEntity.setCode(cityDTO.getCode());
				cityEntity.setDescription(cityDTO.getDescription());
				cityEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(cityEntity);

				cityDAO.saveCityEntity(cityEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "City Save Successfully";
			} else if (DeleteStatusEnum.DELETE.getCode().equals(cityEntity.getStatusEntity().getCode())) {

				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(cityDTO.getStatusCode());

				cityEntity.setCode(cityDTO.getCode());
				cityEntity.setDescription(cityDTO.getDescription());
				cityEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(cityEntity);

				cityDAO.updateCityEntity(cityEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "City Save Successfully";
			} else {
				description = "City Code is Already Used ";
			}

		} catch (Exception e) {
			System.err.println("City Save Issue");
		}
		return new ResponseDTO<CityDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<CityDTO> updateCity(CityDTO cityDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "City Update Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(cityDTO.getStatusCode());

			CityEntity cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());
			cityEntity.setCode(cityDTO.getCode());
			cityEntity.setDescription(cityDTO.getDescription());
			cityEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(cityEntity);

			cityDAO.updateCityEntity(cityEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "City Update Successfully";
		} catch (Exception e) {
			System.err.println("City Update Issue");
		}
		return new ResponseDTO<CityDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<CityDTO> deleteCity(CityDTO cityDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "City Delete Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

			CityEntity cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());

			cityEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(cityEntity);

			cityDAO.updateCityEntity(cityEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "City Delete Successfully";
		} catch (Exception e) {
			System.err.println("City Delete Issue");
		}
		return new ResponseDTO<CityDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<CityDTO> findCity(CityDTO cityDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "City is Not Found";
		CityDTO dto = new CityDTO();
		try {

			CityEntity cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());

			if (cityEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(cityEntity.getStatusEntity().getCode())) {
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "City is Found Successfully";

				dto.setCode(cityEntity.getCode());
				dto.setDescription(cityEntity.getDescription());
				dto.setStatusCode(cityEntity.getStatusEntity().getCode());
				dto.setStatusDescription(cityEntity.getStatusEntity().getDescription());
				dto.setCreatedBy(cityEntity.getCreatedBy());
				dto.setCreatedOn(cityEntity.getCreatedOn());
				dto.setUpdatedBy(cityEntity.getUpdatedBy());
				dto.setUpdatedOn(cityEntity.getUpdatedOn());

			}

		} catch (Exception e) {
			System.err.println("City Find Issue");
		}
		return new ResponseDTO<CityDTO>(code, description, dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForCity() {

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
			System.err.println("City Ref Data Issue");
		}
		return new ResponseDTO<HashMap<String, Object>>(code, map);
	}

	@Override
	@Transactional
	public DataTableResponseDTO getCitysForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<CityDTO> list = new ArrayList<>();
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord = Long.valueOf(0);
		try {
			list = cityDAO.<List<CityEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						CityDTO dto = new CityDTO();
						dto.setCode(entity.getCode());
						dto.setDescription(entity.getDescription());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = cityDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

			responseDTO.setData(list);
			responseDTO.setRecordsTotal(numOfRecord);
			responseDTO.setRecordsFiltered(numOfRecord);
			responseDTO.setDraw(dataTableRequestDTO.getDraw());

		} catch (Exception e) {
		}

		return responseDTO;
	}

}
