package com.leaf.eexamen.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dao.SysRoleAuthorityDAO;
import com.leaf.eexamen.dao.SysRoleDAO;
import com.leaf.eexamen.dto.SysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.entity.SysRoleEntity;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.SysRoleService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	private SysRoleDAO sysRoleDAO;
	private StatusDAO statusDAO;
	private SysRoleAuthorityDAO sysRoleAuthorityDAO;
	private StatusCategoryDAO statusCategoryDAO;

	private CommonMethod commonMethod;

	@Autowired
	public SysRoleServiceImpl(SysRoleDAO sysRoleDAO, StatusDAO statusDAO, SysRoleAuthorityDAO sysRoleAuthorityDAO,
			StatusCategoryDAO statusCategoryDAO, CommonMethod commonMethod) {
		super();
		this.sysRoleDAO = sysRoleDAO;
		this.statusDAO = statusDAO;
		this.sysRoleAuthorityDAO = sysRoleAuthorityDAO;
		this.statusCategoryDAO = statusCategoryDAO;
		this.commonMethod = commonMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SysRoleDTO> saveSysRole(SysRoleDTO sysRoleDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "User Role Save Faield";

		SysRoleEntity sysRoleEntity;
		try {
			sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysRoleDTO.getCode());
			if (sysRoleEntity == null) {
				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sysRoleDTO.getStatusCode());

				sysRoleEntity = new SysRoleEntity();
				sysRoleEntity.setCode(sysRoleDTO.getCode());
				sysRoleEntity.setDescription(sysRoleDTO.getDescription());
				sysRoleEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(sysRoleEntity);

				sysRoleDAO.saveSysRoleEntity(sysRoleEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "User Role Save Successfully";
			} else if (DeleteStatusEnum.DELETE.getCode().equals(sysRoleEntity.getStatusEntity().getCode())) {

				StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sysRoleDTO.getStatusCode());

				sysRoleEntity.setCode(sysRoleDTO.getCode());
				sysRoleEntity.setDescription(sysRoleDTO.getDescription());
				sysRoleEntity.setStatusEntity(statusEntity);

				commonMethod.getPopulateEntityWhenInsert(sysRoleEntity);

				sysRoleDAO.updateSysRoleEntity(sysRoleEntity);
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "User Role Save Successfully";
			} else {
				description = "User Role Code is Already Used ";
			}

		} catch (Exception e) {
			System.err.println("User Role Save Issue");
		}
		return new ResponseDTO<SysRoleDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SysRoleDTO> updateSysRole(SysRoleDTO sysRoleDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "User Role Update Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sysRoleDTO.getStatusCode());

			SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysRoleDTO.getCode());
			sysRoleEntity.setCode(sysRoleDTO.getCode());
			sysRoleEntity.setDescription(sysRoleDTO.getDescription());
			sysRoleEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(sysRoleEntity);

			sysRoleDAO.updateSysRoleEntity(sysRoleEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "User Role Update Successfully";
		} catch (Exception e) {
			System.err.println("User Role Update Issue");
		}
		return new ResponseDTO<SysRoleDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SysRoleDTO> deleteSysRole(SysRoleDTO sysRoleDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "User Role Delete Faield";
		try {
			StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());
			SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysRoleDTO.getCode());
			
			sysRoleAuthorityDAO.deleteSysRoleAuthorityEntityBySysRole(sysRoleEntity.getId());
			sysRoleEntity.setStatusEntity(statusEntity);

			commonMethod.getPopulateEntityWhenUpdate(sysRoleEntity);

			sysRoleDAO.updateSysRoleEntity(sysRoleEntity);
			code = ResponseCodeEnum.SUCCESS.getCode();
			description = "User Role Delete Successfully";
		} catch (Exception e) {
			System.err.println("User Role Delete Issue");
		}
		return new ResponseDTO<SysRoleDTO>(code, description);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<SysRoleDTO> findSysRole(SysRoleDTO sysRoleDTO) {
		String code = ResponseCodeEnum.FAILED.getCode();
		String description = "User Role is Not Found";
		SysRoleDTO dto = new SysRoleDTO();
		try {

			SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysRoleDTO.getCode());

			if (sysRoleEntity != null
					&& !DeleteStatusEnum.DELETE.getCode().equals(sysRoleEntity.getStatusEntity().getCode())) {
				code = ResponseCodeEnum.SUCCESS.getCode();
				description = "User Role is Found Successfully";

				dto.setCode(sysRoleEntity.getCode());
				dto.setDescription(sysRoleEntity.getDescription());
				dto.setStatusCode(sysRoleEntity.getStatusEntity().getCode());
				dto.setStatusDescription(sysRoleEntity.getStatusEntity().getDescription());
				dto.setCreatedBy(sysRoleEntity.getCreatedBy());
				dto.setCreatedOn(sysRoleEntity.getCreatedOn());
				dto.setUpdatedBy(sysRoleEntity.getUpdatedBy());
				dto.setUpdatedOn(sysRoleEntity.getUpdatedOn());

			}

		} catch (Exception e) {
			System.err.println("User Role Find Issue");
		}
		return new ResponseDTO<SysRoleDTO>(code, description, dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public ResponseDTO<HashMap<String, Object>> getReferenceDataForSysRole() {

		HashMap<String, Object> map = new HashMap<>();
		String code = ResponseCodeEnum.FAILED.getCode();
		try {
			List<DropDownDTO> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
					.getStatusEntities().stream()
					.sorted(Comparator.comparing(StatusEntity::getDescription))
					.map(s -> new DropDownDTO(s.getCode(), s.getDescription()))
					.collect(Collectors.toList());

			map.put("status", status);

			code = ResponseCodeEnum.SUCCESS.getCode();
		} catch (Exception e) {
			System.err.println("User Role Ref Data Issue");
		}
		return new ResponseDTO<HashMap<String, Object>>(code, map);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public DataTableResponseDTO getSysRolesForDataTable(DataTableRequestDTO dataTableRequestDTO) {
		List<SysRoleDTO> list = new ArrayList<>();
		DataTableResponseDTO responseDTO = new DataTableResponseDTO();
		Long numOfRecord = Long.valueOf(0);
		try {
			list = sysRoleDAO.<List<SysRoleEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
					.stream().map(entity -> {
						SysRoleDTO dto = new SysRoleDTO();
						dto.setCode(entity.getCode());
						dto.setDescription(entity.getDescription());
						dto.setStatusDescription(entity.getStatusEntity().getDescription());
						dto.setCreatedBy(entity.getCreatedBy());
						dto.setCreatedOn(entity.getCreatedOn());
						dto.setUpdatedBy(entity.getUpdatedBy());
						dto.setUpdatedOn(entity.getUpdatedOn());
						return dto;
					}).collect(Collectors.toList());

			numOfRecord = sysRoleDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

			responseDTO.setData(list);
			responseDTO.setRecordsTotal(numOfRecord);
			responseDTO.setRecordsFiltered(numOfRecord);
			responseDTO.setDraw(dataTableRequestDTO.getDraw());

		} catch (Exception e) {
			System.err.println("User Role Data Table Issue");
		}

		return responseDTO;
	}

}
