package com.leaf.eexamen.service;

import java.util.HashMap;

import com.leaf.eexamen.dto.SysUserAuthorityDTO;
import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

public interface SysUserAuthorityService {

	/**
	 * Get All related and non related System User Authorities For System User.
	 * related authorities are marked as enable true other are false
	 * 
	 * @param sysUserDTO
	 * @return{@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getSysUserAuthorityForSysUser(SysUserDTO sysUserDTO);
	
	/**
	 * Getting Reference Data For SysRole Authority Page
	 * @return {@link ResponseDTO<HashMap<String, Object>>}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForSysUserAuthority();
	
	/**
	 * Save SysUser Authority
	 * 
	 * @param sysUserAuthorityDTO
	 * @return {@link ResponseDTO<SysUserAuthorityDTO>}
	 */
	ResponseDTO<SysUserAuthorityDTO> saveSysUserAuthority(SysUserAuthorityDTO sysUserAuthorityDTO);
	
	/**
	 * Delete SysUserhority
	 * 
	 * @param sysUserAuthorityDTO
	 * @return {@link ResponseDTO<SysUserAuthorityDTO>}
	 */
	ResponseDTO<SysUserAuthorityDTO> deleteSysUserAuthority(SysUserAuthorityDTO sysUsereAuthorityDTO);
}
