package com.leaf.eexamen.dao;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.StudentEntity;
import com.leaf.eexamen.entity.SysUserEntity;

import java.util.List;

public interface StudentDAO {


    /**
     * Find Entity from DB By username
     *
     * @param username
     * @return {@link StudentEntity}
     */
    StudentEntity getStudentEntityByUsername(String username);


    /**
     * Save Student Entity
     *
     * @param studentEntity
     */
    void saveStudentEntity(StudentEntity studentEntity);

    /**
     * Update Student Entity
     *
     * @param studentEntity
     */
    void updateStudentEntity(StudentEntity studentEntity);


    List<SysUserEntity> findStudents(String statusCode);

    /**
     * Getting Data For  Grid
     *
     * @param dataTableRequestDTO
     * @return
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);

}
