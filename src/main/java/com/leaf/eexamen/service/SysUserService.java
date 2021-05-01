package com.leaf.eexamen.service;

import java.util.HashMap;

import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

public interface SysUserService {
	
	
	/**
	 * Save System User
	 * @param sysUserDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SysUserDTO> saveSysUser(SysUserDTO sysUserDTO);
	
	/**
	 * Update System User
	 * @param sysUserDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SysUserDTO> updateSysUser(SysUserDTO sysUserDTO);
	
	/**
	 * Delete System User
	 * @param sysUserDTO - username
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SysUserDTO> deleteSysUser(SysUserDTO sysUserDTO);
	
	/**
	 * Find System User By username
	 * @param sysUserDTO - username
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SysUserDTO> findSysUser(SysUserDTO sysUserDTO);

	/**
	 * Load Reference Data For SysUser Page
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForSysUser();	
	
	/**
	 * Get System Users Data For Data Table
	 * @param dataTableRequestDTO
	 * @return {@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getSysUsersForDataTable(DataTableRequestDTO dataTableRequestDTO);
	
}
