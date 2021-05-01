package com.leaf.eexamen.dao;

import java.util.List;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.TitleEntity;

public interface TitleDAO {
	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link TitleEntity}
	 */
    TitleEntity loadTitleEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link TitleEntity}
	 */
    TitleEntity findTitleEntity(long id);

	/**
	 * Find Entity from DB By Code
	 * @param code
	 * @return {@link TitleEntity}
	 */
    TitleEntity findTitleEntityByCode(String code);
    
    /**
     * Save Title Entity
     * @param titleEntity
     */
    void saveTitleEntity(TitleEntity titleEntity);
    
    /**
     * Update Title Entity
     * @param titleEntity
     */
    void updateTitleEntity(TitleEntity titleEntity);
    
    /**
     * select - *
     * From - Title
     * where - STATUS <> DELETE
     * 
     * Find Title Entities without delete
     * @return {@link List}
     */
    List<TitleEntity> findAllTitleEntities();
    
    
    /**
     * select - *
     * From - Title
     * where - STATUS = statusCode
     * 
     * Find Title Entities By Status Code
     * @return {@link List}
     */
    List<TitleEntity> findAllTitleEntities(String statusCode);
    
    /**
     * Getting Data For  Grid 
     * @param dataTableRequestDTO
     * @return 
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);
}
