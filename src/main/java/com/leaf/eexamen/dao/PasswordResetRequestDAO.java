package com.leaf.eexamen.dao;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.PasswordResetRequestEntity;

import java.util.List;

public interface PasswordResetRequestDAO {


    /**
     * Save PasswordResetRequest Entity
     *
     * @param passwordResetRequestEntity
     */
    void savePasswordResetRequestEntity(PasswordResetRequestEntity passwordResetRequestEntity);


    /**
     * Update PasswordResetRequest Entity
     *
     * @param passwordResetRequestEntity
     */
    void updatePasswordResetRequestEntity(PasswordResetRequestEntity passwordResetRequestEntity);


    /**
     * Select - *
     * From - PasswordResetRequestEntity
     * Where -  STATUS =statusCode and  Username =  username
     * <p>
     * Select all PasswordResetRequestEntity for SysUser Entity and Status
     *
     * @param username
     * @param statusCode
     * @return {@link List<PasswordResetRequestEntity>}
     */
    List<PasswordResetRequestEntity> findAllPasswordResetRequestsBySysUserAndStatus(String username, String statusCode);


    /**
     * Find Entity from DB By Id
     *
     * @param id
     * @return {@link PasswordResetRequestEntity}
     */
    PasswordResetRequestEntity findPasswordResetRequest(long id);


    /**
     * Getting Data For  Grid
     *
     * @param dataTableRequestDTO
     * @return
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);

}
