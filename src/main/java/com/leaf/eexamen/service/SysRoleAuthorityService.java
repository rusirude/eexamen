package com.leaf.eexamen.service;

import java.util.HashMap;

import com.leaf.eexamen.dto.SysRoleAuthorityDTO;
import com.leaf.eexamen.dto.SysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

public interface SysRoleAuthorityService {

	/**
	 * Get All related and non related System Role Authorities For System Role.
	 * related authorities are marked as enable true other are false
	 * 
	 * @param sysRoleDTO
	 * @return{@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getSysRoleAuthorityForSysRole(SysRoleDTO sysRoleDTO);
	
	/**
	 * Getting Reference Data For SysRole Authority Page
	 * @return {@link ResponseDTO<HashMap<String, Object>>}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForSysRoleAuthority();
	
	/**
	 * Save SysRole Authority
	 * 
	 * @param sysRoleAuthorityDTO
	 * @return {@link ResponseDTO<SysRoleAuthorityDTO>}
	 */
	ResponseDTO<SysRoleAuthorityDTO> saveSysRoleAuthority(SysRoleAuthorityDTO sysRoleAuthorityDTO);
	
	/**
	 * Delete SysRole Authority
	 * 
	 * @param sysRoleAuthorityDTO
	 * @return {@link ResponseDTO<SysRoleAuthorityDTO>}
	 */
	ResponseDTO<SysRoleAuthorityDTO> deleteSysRoleAuthority(SysRoleAuthorityDTO sysRoleAuthorityDTO);
}
