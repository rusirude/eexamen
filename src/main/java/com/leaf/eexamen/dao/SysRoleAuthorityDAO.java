package com.leaf.eexamen.dao;

import java.util.List;

import com.leaf.eexamen.entity.SysRoleAuthorityEntity;
import com.leaf.eexamen.entity.SysRoleEntity;

public interface SysRoleAuthorityDAO {
	
	/**
	 * Select - *
	 * From - SysRoleAuthorityEntity
	 * Where - Authority STATUS = ACTIVE and SYS_ROLE STATUS = ACTIVE and SYS_ROLE IN (sysRoleEntities)
	 * 
	 * Select all SysRoleAuthorityEntity for SysRole Entities and Authority and SYSROLE both Status are ACTIVE
	 * @param sysRoleEntities,authorityStatus,sysRoleStatus
	 * @return {@link List<SysRoleAuthorityEntity>]
	 */
	List<SysRoleAuthorityEntity> getSysRoleAuthorityEntitiesBySysRolesAndAnuthorityStatusAndSysRoleStatus(List<SysRoleEntity> sysRoleEntities,String authorityStatus, String sysRoleStatus);
	
	/**
	 * Select - *
	 * From - SysRoleAuthorityEntity
	 * Where - Authority STATUS = ACTIVE and SYS_ROLE IN (sysRoleEntities)
	 * 
	 * Select all SysRoleAuthorityEntity for SysRole Entities and Authority Status are ACTIVE
	 * @param sysRoleEntities
	 * @return {@link List<SysRoleAuthorityEntity>]
	 */
	List<SysRoleAuthorityEntity> getSysRoleAuthorityEntitiesBySysRoles(List<SysRoleEntity> sysRoleEntities);
	
	/**
	 * Select - *
	 * From - SysRoleAuthorityEntity
	 * Where - Authority STATUS = ACTIVE and SYS_ROLE =  SYS_ROLE_ID
	 * 
	 * Select all SysRoleAuthorityEntity for SysRole Entity and AuthorityStatus is ACTIVE
	 * @param sysRoleId
	 * @return {@link List<SysRoleAuthorityEntity>}
	 */
	List<SysRoleAuthorityEntity> getSysRoleAuthorityEntitiesBySysRole(long sysRoleId);
	
	/**
	 * where Authority = authorityId
	 * 
	 * Delete Entities by Authority
	 * @param authorityId
	 */
	void deleteSysRoleAuthorityEntityByAuthority(long authorityId);
	
	/**
	 * where SysRole = sysRoleId
	 * 
	 * Delete Entities by SysRole
	 * @param sysRoleId
	 */
	void deleteSysRoleAuthorityEntityBySysRole(long sysRoleId);
	
	/**
	 * where SysRole = sysRoleId and authorityId
	 * 
	 * Delete Entities by SysRole and Authority
	 * @param sysRoleId,authorityId
	 */
	void deleteSysRoleAuthorityEntityBySysRoleAndAuthority(long sysRoleId, long authorityId);
	
	/**
	 * Save SysRoleAuthority Entity
	 * @param sysRoleAuthorityEntity
	 */
	void saveSysRoleAuthorityentity(SysRoleAuthorityEntity sysRoleAuthorityEntity);
	
	
}
