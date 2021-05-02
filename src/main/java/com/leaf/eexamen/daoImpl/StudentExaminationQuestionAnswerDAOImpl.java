package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.StudentExaminationQuestionAnswerDAO;
import com.leaf.eexamen.entity.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentExaminationQuestionAnswerDAOImpl implements StudentExaminationQuestionAnswerDAO {


    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentExaminationQuestionAnswerEntity loadStudentExaminationQuestionAnswerEntity(long id) {
        return entityManager.getReference(StudentExaminationQuestionAnswerEntity.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentExaminationQuestionAnswerEntity findStudentExaminationQuestionAnswerEntity(long id) {
        return entityManager.find(StudentExaminationQuestionAnswerEntity.class, id);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public StudentExaminationQuestionAnswerEntity findStudentExaminationQuestionAnswerEntityByStudentExaminationAndSeq(long studentExam,int seq){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentExaminationQuestionAnswerEntity> criteriaQuery = criteriaBuilder.createQuery(StudentExaminationQuestionAnswerEntity.class);
        Root<StudentExaminationQuestionAnswerEntity> root = criteriaQuery.from(StudentExaminationQuestionAnswerEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(StudentExaminationQuestionAnswerEntity_.studentExaminationEntity).get(StudentExaminationEntity_.id), studentExam),
                        criteriaBuilder.equal(root.get(StudentExaminationQuestionAnswerEntity_.seq),seq)
                ));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentExaminationQuestionAnswerEntity findFirstStudentExaminationQuestionAnswerEntityByStudentExaminationAndAnswerIsNull(long studentExam){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentExaminationQuestionAnswerEntity> criteriaQuery = criteriaBuilder.createQuery(StudentExaminationQuestionAnswerEntity.class);
        Root<StudentExaminationQuestionAnswerEntity> root = criteriaQuery.from(StudentExaminationQuestionAnswerEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(StudentExaminationQuestionAnswerEntity_.studentExaminationEntity).get(StudentExaminationEntity_.id), studentExam),
                        criteriaBuilder.isNull(root.get(StudentExaminationQuestionAnswerEntity_.questionAnswerEntity))
                ));
        try{
            return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }
        catch (Exception e){
            log.info(e.getMessage());
            return null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentExaminationQuestionAnswerEntity> findStudentExaminationQuestionAnswerEntityByStudentExamination(long studentExam) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentExaminationQuestionAnswerEntity> criteriaQuery = criteriaBuilder.createQuery(StudentExaminationQuestionAnswerEntity.class);
        Root<StudentExaminationQuestionAnswerEntity> root = criteriaQuery.from(StudentExaminationQuestionAnswerEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.equal(root.get(StudentExaminationQuestionAnswerEntity_.studentExaminationEntity).get(StudentExaminationEntity_.id), studentExam)
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentExaminationQuestionAnswerEntity> findStudentExaminationQuestionAnswerEntityByStudentExaminationAndAnswerIsNull(long studentExam) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentExaminationQuestionAnswerEntity> criteriaQuery = criteriaBuilder.createQuery(StudentExaminationQuestionAnswerEntity.class);
        Root<StudentExaminationQuestionAnswerEntity> root = criteriaQuery.from(StudentExaminationQuestionAnswerEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(StudentExaminationQuestionAnswerEntity_.studentExaminationEntity).get(StudentExaminationEntity_.id), studentExam),
                        criteriaBuilder.isNull(root.get(StudentExaminationQuestionAnswerEntity_.questionAnswerEntity))
                )
        );
        try{
            return entityManager.createQuery(criteriaQuery).getResultList();
        }
        catch (Exception e){
            log.info(e.getMessage());
            return Collections.emptyList();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveStudentExaminationQuestionAnswerEntity(StudentExaminationQuestionAnswerEntity studentExaminationQuestionAnswerEntity) {
        entityManager.persist(studentExaminationQuestionAnswerEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStudentExaminationQuestionAnswerEntity(StudentExaminationQuestionAnswerEntity studentExaminationQuestionAnswerEntity) {
        entityManager.merge(studentExaminationQuestionAnswerEntity);
    }
}
