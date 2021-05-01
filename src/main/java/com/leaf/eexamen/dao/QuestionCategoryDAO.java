package com.leaf.eexamen.dao;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.QuestionCategoryEntity;

import java.util.List;

public interface QuestionCategoryDAO {
	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link QuestionCategoryEntity}
	 */
    QuestionCategoryEntity loadQuestionCategoryEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link QuestionCategoryEntity}
	 */
    QuestionCategoryEntity findQuestionCategoryEntity(long id);

	/**
	 * Find Entity from DB By Code
	 * @param code
	 * @return {@link QuestionCategoryEntity}
	 */
    QuestionCategoryEntity findQuestionCategoryEntityByCode(String code);
    
    /**
     * Save QuestionCategory Entity
     * @param countryEntity
     */
    void saveQuestionCategoryEntity(QuestionCategoryEntity countryEntity);
    
    /**
     * Update QuestionCategory Entity
     * @param countryEntity
     */
    void updateQuestionCategoryEntity(QuestionCategoryEntity countryEntity);
    
    /**
     * select - *
     * From - QuestionCategory
     * where - STATUS <> DELETE
     * 
     * Find QuestionCategory Entities without delete
     * @return {@link List}
     */
    List<QuestionCategoryEntity> findAllQuestionCategoryEntities();
    
    
    /**
     * select - *
     * From - QuestionCategory
     * where - STATUS = statusCode
     * 
     * Find QuestionCategory Entities By Status Code
     * @return {@link List}
     */
    List<QuestionCategoryEntity> findAllQuestionCategoryEntities(String statusCode);
    
    /**
     * Getting Data For  Grid 
     * @param dataTableRequestDTO
     * @return 
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);
}
