package com.leaf.eexamen.dao;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.SysUserEntity;

import java.util.List;

public interface SysUserDAO {


    /**
     * Find Entity from DB By username
     *
     * @param username
     * @return {@link SysUserEntity}
     */
    SysUserEntity getSysUserEntityByUsername(String username);


    /**
     * Save SysUser Entity
     *
     * @param sysUserEntity
     */
    void saveSysUserEntity(SysUserEntity sysUserEntity);

    /**
     * Update SysUser Entity
     *
     * @param sysUserEntity
     */
    void updateSysUserEntity(SysUserEntity sysUserEntity);

    /**
     * select - *
     * From - SysUser
     * where - STATUS = statusCode
     * <p>
     * Find SysUser Entities By Status Code
     *
     * @return {@link List}
     */
    List<SysUserEntity> findAllSysUsereEntities(String statusCode);

    /**
     * Getting Data For  Grid
     *
     * @param dataTableRequestDTO
     * @return
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);

}
