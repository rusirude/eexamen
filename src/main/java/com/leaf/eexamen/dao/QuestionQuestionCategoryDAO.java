package com.leaf.eexamen.dao;

import com.leaf.eexamen.entity.QuestionQuestionCategoryEntity;

import java.util.List;

public interface QuestionQuestionCategoryDAO {
	


	
	/**
	 * where SysRole = sysRoleId
	 * 
	 * Delete Entities by SysRole
	 * @param questionId
	 */
	void deleteQuestionQuestionCategoryEntityByQuestion(long questionId);
	

	
	/**
	 * Save QuestionQuestionCategory Entity
	 * @param questionQuestionCategoryEntity
	 */
	void saveQuestionQuestionCategoryEntity(QuestionQuestionCategoryEntity questionQuestionCategoryEntity);

	/**
	 * Select - *
	 * From - QuestionQuestionCategoryEntity
	 * Where - Question Category STATUS = ACTIVE and Question =  questionId
	 *
	 * Select all QuestionQuestionCategoryEntity for Question Entity and Question Category  Status is ACTIVE
	 * @param questionId
	 * @return {@link List<QuestionQuestionCategoryEntity>}
	 */
	List<QuestionQuestionCategoryEntity> getQuestionQuestionCategoryEntity(long questionId);

	/**
	 * Select - *
	 * From - QuestionQuestionCategoryEntity
	 * Where - Question Category STATUS = ACTIVE and QuestionCategory =  category
	 category
	 * Select all QuestionQuestionCategoryEntity for Question Category and Question  Status is ACTIVE
	 * @param category
	 * @return {@link List<QuestionQuestionCategoryEntity>}
	 */
	List<QuestionQuestionCategoryEntity> getQuestionQuestionCategoryEntityByCategory(long category);

	/**
	 * select - count(*)
	 * From - Question
	 * where - STATUS = statusCode and Category
	 *
	 * Find Question Entities Count By Status Code and Question Category
	 * @return {@link Integer}
	 */
	Long getQuestionEntityCountByQuestionCategoryAndStatus(String categoryCode, String statusCode);
	
	
}
