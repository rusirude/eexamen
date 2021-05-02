package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.QuestionAnswerDAO;
import com.leaf.eexamen.entity.*;
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
public class QuestionAnswerDAOImpl implements QuestionAnswerDAO {
	

	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionAnswerEntity loadQuestionAnswerEntity(long id) {
		return entityManager.getReference(QuestionAnswerEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionAnswerEntity findQuestionAnswerEntity(long id) {
		return entityManager.find(QuestionAnswerEntity.class,id);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveQuestionAnswerEntity(QuestionAnswerEntity countryEntity) {
		entityManager.persist(countryEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateQuestionAnswerEntity(QuestionAnswerEntity countryEntity) {
		entityManager.merge(countryEntity);
	}


	@Override
	public List<QuestionAnswerEntity> findAllQuestionAnswerEntitiesByQuestion(long question, String statusCode) {
		List<QuestionAnswerEntity> qestionAnswerEntities = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuestionAnswerEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionAnswerEntity.class);
		Root<QuestionAnswerEntity> root = criteriaQuery.from(QuestionAnswerEntity.class);
		criteriaQuery.select(root);
		criteriaQuery.where(
				criteriaBuilder.and(
						criteriaBuilder.equal(root.get(QuestionAnswerEntity_.statusEntity).get(StatusEntity_.code), statusCode),
						criteriaBuilder.equal(root.get(QuestionAnswerEntity_.questionEntity).get(QuestionEntity_.id), question)

				)
		);

		try {
			qestionAnswerEntities = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return qestionAnswerEntities;
	}

	@Override
	public List<QuestionAnswerEntity> findAllQuestionAnswerEntitiesByQuestionAndNotInAnswers(long question, List<Long> answers) {
		List<QuestionAnswerEntity> qestionAnswerEntities = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuestionAnswerEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionAnswerEntity.class);
		Root<QuestionAnswerEntity> root = criteriaQuery.from(QuestionAnswerEntity.class);
		criteriaQuery.select(root);
		CriteriaBuilder.In<Long> idIn = criteriaBuilder.in(root.get(QuestionAnswerEntity_.id));
		answers.forEach(idIn::value);
		criteriaQuery.where(
				criteriaBuilder.and(
						criteriaBuilder.equal(root.get(QuestionAnswerEntity_.questionEntity).get(QuestionEntity_.id), question),
						criteriaBuilder.not(idIn)

				)
		);

		try {
			qestionAnswerEntities = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return qestionAnswerEntities;
	}

	@Override
	public QuestionAnswerEntity findCorrectQuestionAnswerEntity(long question, String statusCode) {
		QuestionAnswerEntity qestionAnswerEntity = null;
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<QuestionAnswerEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionAnswerEntity.class);
		Root<QuestionAnswerEntity> root = criteriaQuery.from(QuestionAnswerEntity.class);
		criteriaQuery.select(root);

		criteriaQuery.where(
				criteriaBuilder.and(
						criteriaBuilder.equal(root.get(QuestionAnswerEntity_.questionEntity).get(QuestionEntity_.id), question),
						criteriaBuilder.equal(root.get(QuestionAnswerEntity_.statusEntity).get(StatusEntity_.code), statusCode),
						criteriaBuilder.equal(root.get(QuestionAnswerEntity_.correct), true)

				)
		);
		try {
			qestionAnswerEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return qestionAnswerEntity;
	}

	@Override
	public Long findMaxQuestionAnswerForQuestion(String status) {
		String sql = "SELECT " +
						"COUNT(*) AS ID " +
					  "FROM " +
						"question_answer qa INNER JOIN " +
						"status s ON qa.status=s.id " +
				      "WHERE " +
						"s.code = :status " +
				      "GROUP BY " +
				        "qa.question " +
				      "ORDER BY " +
				        "ID DESC " +
				      "LIMIT 1";

		return ((Number)entityManager.createNativeQuery(sql)
				.setParameter("status",status)
				.getSingleResult()).longValue();
	}

	@Override
	public Long findMaxQuestionAnswerForQuestionForQuestionCategory(long category, String status) {
		return null;
	}


}
