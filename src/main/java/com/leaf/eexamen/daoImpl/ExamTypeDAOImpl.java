package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.ExamTypeDAO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.ExamTypeEntity;
import com.leaf.eexamen.entity.ExamTypeEntity_;
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
import java.util.List;


@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExamTypeDAOImpl implements ExamTypeDAO {


	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExamTypeEntity loadExamTypeEntity(long id) {
		return entityManager.getReference(ExamTypeEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExamTypeEntity findExamTypeEntity(long id) {
		return entityManager.find(ExamTypeEntity.class,id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExamTypeEntity findExamTypeEntityByCode(String code) {
		ExamTypeEntity ExamTypeEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamTypeEntity> criteriaQuery = criteriaBuilder.createQuery(ExamTypeEntity.class);
        Root<ExamTypeEntity> root = criteriaQuery.from(ExamTypeEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                      criteriaBuilder.equal(root.get(ExamTypeEntity_.code), code),
                      criteriaBuilder.notEqual(root.get(ExamTypeEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
                )
        );

        try {
            ExamTypeEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
			log.info(e.getMessage());
        }

        return ExamTypeEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveExamTypeEntity(ExamTypeEntity countryEntity) {
		entityManager.persist(countryEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateExamTypeEntity(ExamTypeEntity countryEntity) {
		entityManager.merge(countryEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExamTypeEntity> findAllExamTypeEntities() {
		List<ExamTypeEntity> examTypeEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamTypeEntity> criteriaQuery = criteriaBuilder.createQuery(ExamTypeEntity.class);
        Root<ExamTypeEntity> root = criteriaQuery.from(ExamTypeEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.notEqual(root.get(ExamTypeEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
        );

        try {
        	examTypeEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
        }

        return examTypeEntities;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExamTypeEntity> findAllExamTypeEntities(String statusCode){
		List<ExamTypeEntity> countryEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ExamTypeEntity> criteriaQuery = criteriaBuilder.createQuery(ExamTypeEntity.class);
        Root<ExamTypeEntity> root = criteriaQuery.from(ExamTypeEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(ExamTypeEntity_.statusEntity).get(StatusEntity_.code), statusCode)
        );

        try {
			countryEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
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
        CriteriaQuery<ExamTypeEntity> criteriaQuery = criteriaBuilder.createQuery(ExamTypeEntity.class);
        Root<ExamTypeEntity> root = criteriaQuery.from(ExamTypeEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(),criteriaBuilder,root);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.notEqual(root.get(ExamTypeEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode()),
                		criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
        		)

        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));


        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<ExamTypeEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }
	}

	private List<Predicate> createSearchPredicate(String searchValue,CriteriaBuilder cb,Root<ExamTypeEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!searchValue.isEmpty()) {
			predicates.add(cb.like(root.get(ExamTypeEntity_.code),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExamTypeEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExamTypeEntity_.statusEntity).get(StatusEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExamTypeEntity_.createdBy),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(ExamTypeEntity_.updatedBy),"%"+searchValue+"%"));
		}
		else {
			predicates.add(cb.conjunction());
		}
		return predicates;
	}

	private List<Order> createSortOrder(String sortColumnName,String sortOrder, CriteriaBuilder cb,Root<ExamTypeEntity> root){
		List<Order> orders = new ArrayList<>();

		Expression<?> ex = root.get(SysRoleEntity_.updatedOn);


		if("code".equals(sortColumnName)) {
			ex = root.get(ExamTypeEntity_.code);
		}
		else if("description".equals(sortColumnName)) {
			ex = root.get(ExamTypeEntity_.description);
		}
		else if("status".equals(sortColumnName)) {
			ex = root.get(ExamTypeEntity_.statusEntity).get(StatusEntity_.description);
		}
		else if("createdBy".equals(sortColumnName)) {
			ex = root.get(ExamTypeEntity_.createdBy);
		}
		else if("createdOn".equals(sortColumnName)) {
			ex = root.get(ExamTypeEntity_.createdOn);
		}
		else if("updatedBy".equals(sortColumnName)) {
			ex = root.get(ExamTypeEntity_.updatedBy);
		}

		orders.add(("asc".equals(sortOrder))? cb.asc(ex):cb.desc(ex));

		return orders;
	}

}
