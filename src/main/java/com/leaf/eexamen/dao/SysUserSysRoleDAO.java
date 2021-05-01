package com.leaf.eexamen.dao;

import java.util.List;

import com.leaf.eexamen.entity.SysUserSysRoleEntity;

public interface SysUserSysRoleDAO {
		
	/**
	 * Select - *
	 * From - SysUserSysRoleEntity
	 * Where - SysRole STATUS =ACTIVE and SYS_USER Username =  username
	 * 
	 * Select all SysUserSysRoleEntity for SysUser Entity and SysRoleStatus is ACTIVE
	 * @param username
	 * @return {@link List<SysUserSysRoleEntity>}
	 */
	List<SysUserSysRoleEntity> getSysUserSysRoleEntitiesBySysUser(String username);
	
	/**
	 * where sysRole = sysRoleId
	 * 
	 * Delete Entities by SysRole
	 * @param sysRoleId
	 */
	void deleteSysUserSysRoleEntityBySysRole(long sysRoleId);
	
	/**
	 * where SysUser = username
	 * 
	 * Delete Entities by SysUser
	 * @param username
	 */
	void deleteSysUserSysRoleEntityBySysUser(String username);
	
	/**
	 * where SysUser = username and SysRole = sysRoleId
	 * 
	 * Delete Entities by SysUser and SysRole
	 * @param username,sysRoleId
	 */
	void deleteSysUserSysRoleEntityBySysUserAndSysRole(String username, long sysRoleId);
	
	/**
	 * Save SysUserSysRole Entity
	 * @param SysUserSysRoleEntity
	 */
	void saveSysUserSysRoleEntity(SysUserSysRoleEntity sysUserSysRoleEntity);
	
	
}
