package com.leaf.eexamen.dao;

import com.leaf.eexamen.entity.ExamTypeQuestionModelEntity;

import java.util.List;

public interface ExamTypeQuestionModelDAO {
	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link ExamTypeQuestionModelEntity}
	 */
    ExamTypeQuestionModelEntity loadExamTypeQuestionModelEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link ExamTypeQuestionModelEntity}
	 */
    ExamTypeQuestionModelEntity findExamTypeQuestionModelEntity(long id);

    
    /**
     * Save ExamTypeQuestionModel Entity
     * @param examType
     */
    void saveExamTypeQuestionModelEntity(ExamTypeQuestionModelEntity examType);
    
    /**
     * Update ExamTypeQuestionModel Entity
     * @param examType
     */
    void updateExamTypeQuestionModelEntity(ExamTypeQuestionModelEntity examType);


    List<ExamTypeQuestionModelEntity> findAllExamTypeQuestionModelEntitiesByExamTypeAndNotInIds(long examType, List<Long> ids);


    List<ExamTypeQuestionModelEntity> findAllExamTypeQuestionModelEntitiesByExamTypeAndQuestionType(long examType, String questionType, String statusCode);




}
