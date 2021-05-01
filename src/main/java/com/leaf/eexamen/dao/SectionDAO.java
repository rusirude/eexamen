package com.leaf.eexamen.dao;

import java.util.List;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.SectionEntity;

public interface SectionDAO {
	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link SectionEntity}
	 */
    SectionEntity loadSectionEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link SectionEntity}
	 */
    SectionEntity findSectionEntity(long id);

	/**
	 * Find Entity from DB By Code
	 * @param code
	 * @return {@link SectionEntity}
	 */
    SectionEntity findSectionEntityByCode(String code);
    
    /**
     * Save Section Entity
     * @param sectionEntity
     */
    void saveSectionEntity(SectionEntity sectionEntity);
    
    /**
     * Update Section Entity
     * @param sectionEntity
     */
    void updateSectionEntity(SectionEntity sectionEntity);
    
    /**
     * select - *
     * From - Section
     * where - STATUS <> DELETE
     * 
     * Find Section Entities without delete
     * @return {@link List}
     */
    List<SectionEntity> findAllSectionEntities();
    
    
    /**
     * select - *
     * From - Section
     * where - STATUS = statusCode
     * 
     * Find Section Entities By Status Code
     * @return {@link List}
     */
    List<SectionEntity> findAllSectionEntities(String statusCode);
    
    /**
     * Getting Data For  Grid 
     * @param dataTableRequestDTO
     * @return 
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);
}
