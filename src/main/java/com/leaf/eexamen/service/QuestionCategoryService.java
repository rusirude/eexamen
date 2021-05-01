package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.QuestionCategoryDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.HashMap;

public interface QuestionCategoryService {
	/**
	 * Save QuestionCategory
	 * @param questionCategoryDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<QuestionCategoryDTO> saveQuestionCategory(QuestionCategoryDTO questionCategoryDTO);
	
	/**
	 * Update QuestionCategory
	 * @param questionCategoryDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<QuestionCategoryDTO> updateQuestionCategory(QuestionCategoryDTO questionCategoryDTO);
	
	/**
	 * Delete QuestionCategory
	 * @param questionCategoryDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<QuestionCategoryDTO> deleteQuestionCategory(QuestionCategoryDTO questionCategoryDTO);
	
	/**
	 * Find QuestionCategory By Code
	 * @param questionCategoryDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<QuestionCategoryDTO> findQuestionCategory(QuestionCategoryDTO questionCategoryDTO);
	
	/**
	 * Load Reference Data For QuestionCategory Page
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForQuestionCategory();
	
	/**
	 * Get Countries Data For Data Table
	 * @param dataTableRequestDTO
	 * @return {@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getQuestionCategoriesForDataTable(DataTableRequestDTO dataTableRequestDTO);
}
