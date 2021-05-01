package com.leaf.eexamen.dao;

import com.leaf.eexamen.entity.StudentExaminationQuestionAnswerEntity;

import java.util.List;

public interface StudentExaminationQuestionAnswerDAO {
    /**
     * Load Reference Entity
     * @param id
     * @return {@link StudentExaminationQuestionAnswerEntity}
     */
    StudentExaminationQuestionAnswerEntity loadStudentExaminationQuestionAnswerEntity(long id);

    /**
     * Find Entity from DB By Id
     * @param id
     * @return {@link StudentExaminationQuestionAnswerEntity}
     */
    StudentExaminationQuestionAnswerEntity findStudentExaminationQuestionAnswerEntity(long id);

    /**
     * Find Entity from DB By Id
     * @param studentExam
     * @param seq
     * @return {@link StudentExaminationQuestionAnswerEntity}
     */
    StudentExaminationQuestionAnswerEntity findStudentExaminationQuestionAnswerEntityByStudentExaminationAndSeq(long studentExam,int seq);

    /**
     * Find Entity from DB By Id
     * @param studentExam
     * @return {@link StudentExaminationQuestionAnswerEntity}
     */
    StudentExaminationQuestionAnswerEntity findFirstStudentExaminationQuestionAnswerEntityByStudentExaminationAndAnswerIsNull(long studentExam);

    /**
     * Find Entity from DB By Id
     * @param studentExam
     * @return {@link StudentExaminationQuestionAnswerEntity}
     */
    List<StudentExaminationQuestionAnswerEntity> findStudentExaminationQuestionAnswerEntityByStudentExamination(long studentExam);

    /**
     * Find Entity from DB By Id
     * @param studentExam
     * @return {@link StudentExaminationQuestionAnswerEntity}
     */
    List<StudentExaminationQuestionAnswerEntity> findStudentExaminationQuestionAnswerEntityByStudentExaminationAndAnswerIsNull(long studentExam);


    /**
     * Save StudentExaminationQuestionAnswer Entity
     * @param studentExaminationQuestionAnswerEntity
     */
    void saveStudentExaminationQuestionAnswerEntity(StudentExaminationQuestionAnswerEntity studentExaminationQuestionAnswerEntity);

    /**
     * Update StudentExaminationQuestionAnswer Entity
     * @param studentExaminationQuestionAnswerEntity
     */
    void updateStudentExaminationQuestionAnswerEntity(StudentExaminationQuestionAnswerEntity studentExaminationQuestionAnswerEntity);

}
