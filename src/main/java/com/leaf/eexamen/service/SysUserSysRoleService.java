package com.leaf.eexamen.service;

import java.util.HashMap;

import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.SysUserSysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

public interface SysUserSysRoleService {

	/**
	 * Get All related and non related System Roles  For System user.
	 * related system roles are marked as enable true other are false
	 * 
	 * @param sysUserDTO
	 * @return{@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getSysUserSysRoleForSysUser(SysUserDTO sysUserDTO);
	
	/**
	 * Getting Reference Data For System User's System Role Page
	 * @return {@link ResponseDTO<HashMap<String, Object>>}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForSysUserSysRole();
	
	/**
	 * Save SysUser SysRole
	 * 
	 * @param SysUserSysRoleDTO
	 * @return {@link ResponseDTO<SysUserSysRoleDTO>}
	 */
	ResponseDTO<SysUserSysRoleDTO> saveSysUserSysRole(SysUserSysRoleDTO sysUserSysRoleDTO);
	
	/**
	 * Delete SysUser SysRole
	 * 
	 * @param SysUserSysRoleDTO
	 * @return {@link ResponseDTO<SysUserSysRoleDTO>}
	 */
	ResponseDTO<SysUserSysRoleDTO> deleteSysUserSysRole(SysUserSysRoleDTO sysUserSysRoleDTO);
}
