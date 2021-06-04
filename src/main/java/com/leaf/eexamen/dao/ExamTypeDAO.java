package com.leaf.eexamen.dao;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.ExamTypeEntity;

import java.util.List;

public interface ExamTypeDAO {
	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link ExamTypeEntity}
	 */
    ExamTypeEntity loadExamTypeEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link ExamTypeEntity}
	 */
    ExamTypeEntity findExamTypeEntity(long id);

	/**
	 * Find Entity from DB By Code
	 * @param code
	 * @return {@link ExamTypeEntity}
	 */
    ExamTypeEntity findExamTypeEntityByCode(String code);
    
    /**
     * Save ExamType Entity
     * @param examTypeEntity
     */
    void saveExamTypeEntity(ExamTypeEntity examTypeEntity);
    
    /**
     * Update ExamType Entity
     * @param examTypeEntity
     */
    void updateExamTypeEntity(ExamTypeEntity examTypeEntity);
    
    /**
     * select - *
     * From - ExamType
     * where - STATUS <> DELETE
     * 
     * Find ExamType Entities without delete
     * @return {@link List}
     */
    List<ExamTypeEntity> findAllExamTypeEntities();
    
    
    /**
     * select - *
     * From - ExamType
     * where - STATUS = statusCode
     * 
     * Find ExamType Entities By Status Code
     * @return {@link List}
     */
    List<ExamTypeEntity> findAllExamTypeEntities(String statusCode);



    /**
     * Getting Data For  Grid 
     * @param dataTableRequestDTO
     * @return 
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);

}
