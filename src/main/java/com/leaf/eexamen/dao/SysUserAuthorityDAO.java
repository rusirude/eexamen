package com.leaf.eexamen.dao;

import java.util.List;

import com.leaf.eexamen.entity.SysUserAuthorityEntity;

public interface SysUserAuthorityDAO {
	
	
		
	/**
	 * Select - *
	 * From - SysUserAuthorityEntity
	 * Where - SYS_USER  = username and Authority Status = ACTIVE
	 * 
	 * Select all SysUserAuthorityEntity for SYSUSER (username)
	 * @param username
	 * @return {@link List<SysUserAuthorityEntity>]
	 */
	List<SysUserAuthorityEntity> getSysUserAuthorityEntitiesBySysUser(String username);
	
	/**
	 * where Authority = authorityId
	 * 
	 * Delete Entities by Authority
	 * @param authorityId
	 */
	void deleteSysUserAuthorityEntityByAuthority(long authorityId);
	
	/**
	 * where SysUser = username
	 * 
	 * Delete Entities by SysUser
	 * @param username
	 */
	void deleteSysUserAuthorityEntityBySysUser(String username);
	
	/**
	 * where username and authorityId
	 * 
	 * Delete Entities by SysUser and Authority
	 * @param username,authorityId
	 */
	void deleteSysUserAuthorityEntityBySysUserAndAuthority(String username, long authorityId);
	
	/**
	 * Save SysUserAuthority Entity
	 * @param sysUserAuthorityEntity
	 */
	void saveSysUserAuthorityEntity(SysUserAuthorityEntity sysUserAuthorityEntity);

}
