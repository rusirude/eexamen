package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.QuestionCategoryDAO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.QuestionCategoryEntity;
import com.leaf.eexamen.entity.QuestionCategoryEntity_;
import com.leaf.eexamen.entity.StatusEntity_;
import com.leaf.eexamen.entity.SysRoleEntity_;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.utility.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

;

@Repository
public class QuestionCategoryDAOImpl implements QuestionCategoryDAO {
	
	@Autowired
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionCategoryEntity loadQuestionCategoryEntity(long id) {
		return entityManager.getReference(QuestionCategoryEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionCategoryEntity findQuestionCategoryEntity(long id) {
		return entityManager.find(QuestionCategoryEntity.class,id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QuestionCategoryEntity findQuestionCategoryEntityByCode(String code) {
		QuestionCategoryEntity QuestionCategoryEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionCategoryEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionCategoryEntity.class);
        Root<QuestionCategoryEntity> root = criteriaQuery.from(QuestionCategoryEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                      criteriaBuilder.equal(root.get(QuestionCategoryEntity_.code), code),
                      criteriaBuilder.notEqual(root.get(QuestionCategoryEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
                )
        );

        try {
            QuestionCategoryEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
        }

        return QuestionCategoryEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveQuestionCategoryEntity(QuestionCategoryEntity countryEntity) {
		entityManager.persist(countryEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateQuestionCategoryEntity(QuestionCategoryEntity countryEntity) {
		entityManager.merge(countryEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<QuestionCategoryEntity> findAllQuestionCategoryEntities() {
		List<QuestionCategoryEntity> countryEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionCategoryEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionCategoryEntity.class);
        Root<QuestionCategoryEntity> root = criteriaQuery.from(QuestionCategoryEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.notEqual(root.get(QuestionCategoryEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
        );

        try {
        	countryEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return countryEntities;
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<QuestionCategoryEntity> findAllQuestionCategoryEntities(String statusCode){
		List<QuestionCategoryEntity> countryEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionCategoryEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionCategoryEntity.class);
        Root<QuestionCategoryEntity> root = criteriaQuery.from(QuestionCategoryEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(QuestionCategoryEntity_.statusEntity).get(StatusEntity_.code), statusCode)
        );

        try {
			countryEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return countryEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuestionCategoryEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionCategoryEntity.class);
        Root<QuestionCategoryEntity> root = criteriaQuery.from(QuestionCategoryEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(),criteriaBuilder,root);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.notEqual(root.get(QuestionCategoryEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode()),
                		criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
        		)
        		
        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));
        

        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<QuestionCategoryEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }	
	}
	
	private List<Predicate> createSearchPredicate(String searchValue,CriteriaBuilder cb,Root<QuestionCategoryEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!searchValue.isEmpty()) {
			predicates.add(cb.like(root.get(QuestionCategoryEntity_.code),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionCategoryEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionCategoryEntity_.statusEntity).get(StatusEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionCategoryEntity_.createdBy),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(QuestionCategoryEntity_.updatedBy),"%"+searchValue+"%"));
		}		
		else {
			predicates.add(cb.conjunction());
		}
		return predicates;
	}
	
	private List<Order> createSortOrder(String sortColumnName,String sortOrder, CriteriaBuilder cb,Root<QuestionCategoryEntity> root){
		List<Order> orders = new ArrayList<>();		

		Expression<?> ex = root.get(SysRoleEntity_.updatedOn);
		
		
		if("code".equals(sortColumnName)) {
			ex = root.get(QuestionCategoryEntity_.code);
		}
		else if("description".equals(sortColumnName)) {
			ex = root.get(QuestionCategoryEntity_.description);
		}
		else if("status".equals(sortColumnName)) {
			ex = root.get(QuestionCategoryEntity_.statusEntity).get(StatusEntity_.description);
		}
		else if("createdBy".equals(sortColumnName)) {
			ex = root.get(QuestionCategoryEntity_.createdBy);
		}
		else if("createdOn".equals(sortColumnName)) {
			ex = root.get(QuestionCategoryEntity_.createdOn);
		}
		else if("updatedBy".equals(sortColumnName)) {
			ex = root.get(QuestionCategoryEntity_.updatedBy);
		}		
		
		orders.add(("asc".equals(sortOrder))? cb.asc(ex):cb.desc(ex));		
		
		return orders;
	}

}
