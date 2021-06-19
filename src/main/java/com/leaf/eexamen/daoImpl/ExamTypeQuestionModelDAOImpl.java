package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.ExamTypeQuestionModelDAO;
import com.leaf.eexamen.entity.ExamTypeEntity_;
import com.leaf.eexamen.entity.ExamTypeQuestionModelEntity;
import com.leaf.eexamen.entity.ExamTypeQuestionModelEntity_;
import com.leaf.eexamen.entity.StatusEntity_;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExamTypeQuestionModelDAOImpl implements ExamTypeQuestionModelDAO {
	

	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExamTypeQuestionModelEntity loadExamTypeQuestionModelEntity(long id) {
		return entityManager.getReference(ExamTypeQuestionModelEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExamTypeQuestionModelEntity findExamTypeQuestionModelEntity(long id) {
		return entityManager.find(ExamTypeQuestionModelEntity.class,id);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveExamTypeQuestionModelEntity(ExamTypeQuestionModelEntity examTypeQuestionModelEntity) {
		entityManager.persist(examTypeQuestionModelEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateExamTypeQuestionModelEntity(ExamTypeQuestionModelEntity examTypeQuestionModelEntity) {
		entityManager.merge(examTypeQuestionModelEntity);
	}

	@Override
	public List<ExamTypeQuestionModelEntity> findAllExamTypeQuestionModelEntitiesByExamTypeAndNotInIds(long examType, List<Long> ids) {
		List<ExamTypeQuestionModelEntity> examTypeQuestionModelEntities = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExamTypeQuestionModelEntity> criteriaQuery = criteriaBuilder.createQuery(ExamTypeQuestionModelEntity.class);
		Root<ExamTypeQuestionModelEntity> root = criteriaQuery.from(ExamTypeQuestionModelEntity.class);
		criteriaQuery.select(root);
		CriteriaBuilder.In<Long> idIn = criteriaBuilder.in(root.get(ExamTypeQuestionModelEntity_.id));
		ids.forEach(idIn::value);
		criteriaQuery.where(
				criteriaBuilder.and(
						criteriaBuilder.equal(root.get(ExamTypeQuestionModelEntity_.examTypeEntity).get(ExamTypeEntity_.id), examType),
						criteriaBuilder.not(idIn)

				)
		);

		try {
			examTypeQuestionModelEntities = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return examTypeQuestionModelEntities;
	}



	@Override
	public List<ExamTypeQuestionModelEntity> findAllExamTypeQuestionModelEntitiesByExamTypeAndQuestionType(long examType, String questionType, String statusCode) {
		List<ExamTypeQuestionModelEntity> typeQuestionModelEntities = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExamTypeQuestionModelEntity> criteriaQuery = criteriaBuilder.createQuery(ExamTypeQuestionModelEntity.class);
		Root<ExamTypeQuestionModelEntity> root = criteriaQuery.from(ExamTypeQuestionModelEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(
				criteriaBuilder.and(
						criteriaBuilder.equal(root.get(ExamTypeQuestionModelEntity_.statusEntity).get(StatusEntity_.code), statusCode),
						criteriaBuilder.equal(root.get(ExamTypeQuestionModelEntity_.examTypeEntity).get(ExamTypeEntity_.id), examType),
						criteriaBuilder.equal(root.get(ExamTypeQuestionModelEntity_.type), questionType)

				)
		);

		try {
			typeQuestionModelEntities = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return typeQuestionModelEntities;
	}


}
