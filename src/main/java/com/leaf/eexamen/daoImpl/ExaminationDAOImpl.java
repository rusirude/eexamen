package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.ExaminationDAO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.ExaminationEntity;
import com.leaf.eexamen.entity.ExaminationEntity_;
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
public class ExaminationDAOImpl implements ExaminationDAO {
	
	@Autowired
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationEntity loadExaminationEntity(long id) {
		return entityManager.getReference(ExaminationEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationEntity findExaminationEntity(long id) {
		return entityManager.find(ExaminationEntity.class,id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExaminationEntity findExaminationEntityByCode(String code) {
		ExaminationEntity ExaminationEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExaminationEntity> criteriaQuery = criteriaBuilder.createQuery(ExaminationEntity.class);
        Root<ExaminationEntity> root = criteriaQuery.from(ExaminationEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                      criteriaBuilder.equal(root.get(ExaminationEntity_.code), code),
                      criteriaBuilder.notEqual(root.get(ExaminationEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
                )
        );

        try {
            ExaminationEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
        }

        return ExaminationEntity;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveExaminationEntity(ExaminationEntity examinationEntity) {
		entityManager.persist(examinationEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateExaminationEntity(ExaminationEntity examinationEntity) {
		entityManager.merge(examinationEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExaminationEntity> findAllExaminationEntities() {
		List<ExaminationEntity> examinationEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExaminationEntity> criteriaQuery = criteriaBuilder.createQuery(ExaminationEntity.class);
        Root<ExaminationEntity> root = criteriaQuery.from(ExaminationEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.notEqual(root.get(ExaminationEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
        );

        try {
        	examinationEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return examinationEntities;
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExaminationEntity> findAllExaminationEntities(String statusCode){
		List<ExaminationEntity> examinationEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExaminationEntity> criteriaQuery = criteriaBuilder.createQuery(ExaminationEntity.class);
        Root<ExaminationEntity> root = criteriaQuery.from(ExaminationEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(ExaminationEntity_.statusEntity).get(StatusEntity_.code), statusCode)
        );

        try {
			examinationEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return examinationEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExaminationEntity> criteriaQuery = criteriaBuilder.createQuery(ExaminationEntity.class);
        Root<ExaminationEntity> root = criteriaQuery.from(ExaminationEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(),criteriaBuilder,root);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.notEqual(root.get(ExaminationEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode()),
                		criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
        		)
        		
        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));
        

        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<ExaminationEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }	
	}
	
	private List<Predicate> createSearchPredicate(String searchValue,CriteriaBuilder cb,Root<ExaminationEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!searchValue.isEmpty()) {
			predicates.add(cb.like(root.get(ExaminationEntity_.code),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExaminationEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExaminationEntity_.statusEntity).get(StatusEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExaminationEntity_.createdBy),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExaminationEntity_.updatedBy),"%"+searchValue+"%"));
		}		
		else {
			predicates.add(cb.conjunction());
		}
		return predicates;
	}
	
	private List<Order> createSortOrder(String sortColumnName,String sortOrder, CriteriaBuilder cb,Root<ExaminationEntity> root){
		List<Order> orders = new ArrayList<>();		

		Expression<?> ex = root.get(SysRoleEntity_.updatedOn);
		
		
		if("code".equals(sortColumnName)) {
			ex = root.get(ExaminationEntity_.code);
		}
		else if("description".equals(sortColumnName)) {
			ex = root.get(ExaminationEntity_.description);
		}
		else if("status".equals(sortColumnName)) {
			ex = root.get(ExaminationEntity_.statusEntity).get(StatusEntity_.description);
		}
		else if("createdBy".equals(sortColumnName)) {
			ex = root.get(ExaminationEntity_.createdBy);
		}
		else if("createdOn".equals(sortColumnName)) {
			ex = root.get(ExaminationEntity_.createdOn);
		}
		else if("updatedBy".equals(sortColumnName)) {
			ex = root.get(ExaminationEntity_.updatedBy);
		}		
		
		orders.add(("asc".equals(sortOrder))? cb.asc(ex):cb.desc(ex));		
		
		return orders;
	}

}
