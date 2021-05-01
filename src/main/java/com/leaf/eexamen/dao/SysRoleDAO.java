package com.leaf.eexamen.dao;

import java.util.List;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.SysRoleEntity;

public interface SysRoleDAO {

	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link SysRoleEntity}
	 */
    SysRoleEntity loadSysRoleEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link SysRoleEntity}
	 */
    SysRoleEntity findSysRoleEntity(long id);

	/**
	 * Find Entity from DB By Code
	 * @param code
	 * @return {@link SysRoleEntity}
	 */
    SysRoleEntity findSysRoleEntityByCode(String code);
    
    /**
     * Save SysRole Entity
     * @param sysRoleEntity
     */
    void saveSysRoleEntity(SysRoleEntity sysRoleEntity);
    
    /**
     * Update SysRole Entity
     * @param sysRoleEntity
     */
    void updateSysRoleEntity(SysRoleEntity sysRoleEntity);
    
    /**
     * select - *
     * From - SysRole
     * where - STATUS = statusCode
     * 
     * Find SysRole Entities By Status Code
     * @return {@link List}
     */
    List<SysRoleEntity> findAllSysRoleEntities(String statusCode);
    
    /**
     * Getting Data For  Grid 
     * @param dataTableRequestDTO
     * @return 
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);
}
