package com.leaf.eexamen.dao;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.ExaminationEntity;

import java.util.List;

public interface ExaminationDAO {
	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link ExaminationEntity}
	 */
    ExaminationEntity loadExaminationEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link ExaminationEntity}
	 */
    ExaminationEntity findExaminationEntity(long id);

	/**
	 * Find Entity from DB By Code
	 * @param code
	 * @return {@link ExaminationEntity}
	 */
    ExaminationEntity findExaminationEntityByCode(String code);
    
    /**
     * Save Examination Entity
     * @param eaminationEntity
     */
    void saveExaminationEntity(ExaminationEntity eaminationEntity);
    
    /**
     * Update Examination Entity
     * @param eaminationEntity
     */
    void updateExaminationEntity(ExaminationEntity eaminationEntity);
    
    /**
     * select - *
     * From - Examination
     * where - STATUS <> DELETE
     * 
     * Find Examination Entities without delete
     * @return {@link List}
     */
    List<ExaminationEntity> findAllExaminationEntities();
    
    
    /**
     * select - *
     * From - Examination
     * where - STATUS = statusCode
     * 
     * Find Examination Entities By Status Code
     * @return {@link List}
     */
    List<ExaminationEntity> findAllExaminationEntities(String statusCode);
    
    /**
     * Getting Data For  Grid 
     * @param dataTableRequestDTO
     * @return 
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);
}
