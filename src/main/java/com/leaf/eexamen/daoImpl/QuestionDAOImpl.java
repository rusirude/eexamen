package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.QuestionDAO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.QuestionEntity;
import com.leaf.eexamen.entity.QuestionEntity_;
import com.leaf.eexamen.entity.StatusEntity_;
import com.leaf.eexamen.entity.SysRoleEntity_;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionDAOImpl implements QuestionDAO {


	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionEntity loadQuestionEntity(long id) {
		return entityManager.getReference(QuestionEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionEntity findQuestionEntity(long id) {
		return entityManager.find(QuestionEntity.class,id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionEntity findQuestionEntityByCode(String code) {
		QuestionEntity QuestionEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionEntity.class);
        Root<QuestionEntity> root = criteriaQuery.from(QuestionEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                      criteriaBuilder.equal(root.get(QuestionEntity_.code), code),
                      criteriaBuilder.notEqual(root.get(QuestionEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
                )
        );

        try {
            QuestionEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
			log.info(e.getMessage());
        }

        return QuestionEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveQuestionEntity(QuestionEntity countryEntity) {
		entityManager.persist(countryEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateQuestionEntity(QuestionEntity countryEntity) {
		entityManager.merge(countryEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<QuestionEntity> findAllQuestionEntities() {
		List<QuestionEntity> questionEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionEntity.class);
        Root<QuestionEntity> root = criteriaQuery.from(QuestionEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.notEqual(root.get(QuestionEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
        );

        try {
        	questionEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
        }

        return questionEntities;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<QuestionEntity> findAllQuestionEntities(String statusCode){
		List<QuestionEntity> countryEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionEntity.class);
        Root<QuestionEntity> root = criteriaQuery.from(QuestionEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(QuestionEntity_.statusEntity).get(StatusEntity_.code), statusCode)
        );

        try {
			countryEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
        }

        return countryEntities;
	}


	@Override
	public List<QuestionEntity> findAllQuestionEntitiesRandomly(long status, int limit, long category, String group, String label, List<Long> currentAvailableIds) {
		if(Optional.of(label).orElse("").isEmpty()){
			if(Optional.ofNullable(currentAvailableIds).orElse(Collections.emptyList()).isEmpty()){
				String sql = "SELECT " +
						"q.* " +
						"FROM " +
						"question_question_category qqc INNER JOIN " +
						"question q " +
						"on qqc.question = q.id " +
						"WHERE " +
						"qqc.question_category = :category AND " +
						"q.status = :status AND " +
						"q.grp = :grp AND " +
						"q.lab IS NULL " +
						"ORDER BY " +
						"RAND() " +
						"LIMIT :limit";
				return entityManager.createNativeQuery(sql,QuestionEntity.class)
						.setParameter("category", category)
						.setParameter("status",status)
						.setParameter("grp",group)
						.setParameter("limit",limit)
						.getResultList();
			}
			else{
				String sql = "SELECT " +
						"q.* " +
						"FROM " +
						"question_question_category qqc INNER JOIN " +
						"question q " +
						"ON qqc.question = q.id " +
						"WHERE " +
						"qqc.question_category = :category AND " +
						"q.status = :status AND " +
						"q.grp = :grp AND " +
						"q.lab IS NULL AND " +
						"q.id NOT IN(:ids) " +
						"ORDER BY " +
						"RAND() " +
						"LIMIT :limit";
				return entityManager.createNativeQuery(sql,QuestionEntity.class)
						.setParameter("category", category)
						.setParameter("status",status)
						.setParameter("grp",group)
						.setParameter("ids",currentAvailableIds)
						.setParameter("limit",limit)
						.getResultList();
			}

		}
		else {
			if(Optional.ofNullable(currentAvailableIds).orElse(Collections.emptyList()).isEmpty()){
				String sql = "SELECT " +
						"q.* " +
						"FROM " +
						"question_question_category qqc INNER JOIN " +
						"question q " +
						"on qqc.question = q.id " +
						"WHERE " +
						"qqc.question_category = :category AND " +
						"q.status = :status AND " +
						"q.grp = :grp AND " +
						"q.lab = :lab " +
						"ORDER BY " +
						"RAND() " +
						"LIMIT :limit";
				return entityManager.createNativeQuery(sql,QuestionEntity.class)
						.setParameter("category", category)
						.setParameter("status",status)
						.setParameter("grp",group)
						.setParameter("lab",label)
						.setParameter("limit",limit)
						.getResultList();
			}
			else{
				String sql = "SELECT " +
						"q.* " +
						"FROM " +
						"question_question_category qqc INNER JOIN " +
						"question q " +
						"ON qqc.question = q.id " +
						"WHERE " +
						"qqc.question_category = :category AND " +
						"q.status = :status AND " +
						"q.grp = :grp AND " +
						"q.lab = :lab  AND " +
						"q.id NOT IN(:ids) " +
						"ORDER BY " +
						"RAND() " +
						"LIMIT :limit";
				return entityManager.createNativeQuery(sql,QuestionEntity.class)
						.setParameter("category", category)
						.setParameter("status",status)
						.setParameter("grp",group)
						.setParameter("lab",label)
						.setParameter("ids",currentAvailableIds)
						.setParameter("limit",limit)
						.getResultList();
			}
		}

	}



	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionEntity.class);
        Root<QuestionEntity> root = criteriaQuery.from(QuestionEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(),criteriaBuilder,root);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.notEqual(root.get(QuestionEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode()),
                		criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
        		)

        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));


        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<QuestionEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }
	}

	private List<Predicate> createSearchPredicate(String searchValue,CriteriaBuilder cb,Root<QuestionEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!searchValue.isEmpty()) {
			predicates.add(cb.like(root.get(QuestionEntity_.code),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionEntity_.statusEntity).get(StatusEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionEntity_.createdBy),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionEntity_.updatedBy),"%"+searchValue+"%"));
		}
		else {
			predicates.add(cb.conjunction());
		}
		return predicates;
	}

	private List<Order> createSortOrder(String sortColumnName,String sortOrder, CriteriaBuilder cb,Root<QuestionEntity> root){
		List<Order> orders = new ArrayList<>();

		Expression<?> ex = root.get(SysRoleEntity_.updatedOn);


		if("code".equals(sortColumnName)) {
			ex = root.get(QuestionEntity_.code);
		}
		else if("description".equals(sortColumnName)) {
			ex = root.get(QuestionEntity_.description);
		}
		else if("status".equals(sortColumnName)) {
			ex = root.get(QuestionEntity_.statusEntity).get(StatusEntity_.description);
		}
		else if("createdBy".equals(sortColumnName)) {
			ex = root.get(QuestionEntity_.createdBy);
		}
		else if("createdOn".equals(sortColumnName)) {
			ex = root.get(QuestionEntity_.createdOn);
		}
		else if("updatedBy".equals(sortColumnName)) {
			ex = root.get(QuestionEntity_.updatedBy);
		}

		orders.add(("asc".equals(sortOrder))? cb.asc(ex):cb.desc(ex));

		return orders;
	}

}
