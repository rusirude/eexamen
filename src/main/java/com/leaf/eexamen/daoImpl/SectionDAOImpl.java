package com.leaf.eexamen.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.leaf.eexamen.dao.SectionDAO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.SectionEntity;
import com.leaf.eexamen.entity.SectionEntity_;
import com.leaf.eexamen.entity.StatusEntity_;
import com.leaf.eexamen.entity.SysRoleEntity_;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.utility.CommonConstant;

@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SectionDAOImpl implements SectionDAO {
	

	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionEntity loadSectionEntity(long id) {
		return entityManager.getReference(SectionEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionEntity findSectionEntity(long id) {
		return entityManager.find(SectionEntity.class,id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionEntity findSectionEntityByCode(String code) {
		SectionEntity sectionEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SectionEntity> criteriaQuery = criteriaBuilder.createQuery(SectionEntity.class);
        Root<SectionEntity> root = criteriaQuery.from(SectionEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                      criteriaBuilder.equal(root.get(SectionEntity_.code), code),
                      criteriaBuilder.notEqual(root.get(SectionEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
                )
        );

        try {
            sectionEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
			log.info(e.getMessage());
        }

        return sectionEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveSectionEntity(SectionEntity sectionEntity) {
		entityManager.persist(sectionEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateSectionEntity(SectionEntity sectionEntity) {
		entityManager.merge(sectionEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SectionEntity> findAllSectionEntities() {
		List<SectionEntity> sectionEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SectionEntity> criteriaQuery = criteriaBuilder.createQuery(SectionEntity.class);
        Root<SectionEntity> root = criteriaQuery.from(SectionEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.notEqual(root.get(SectionEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
        );

        try {
        	sectionEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
        }
        
        return sectionEntities;
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SectionEntity> findAllSectionEntities(String statusCode){
		List<SectionEntity> sysRoleEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SectionEntity> criteriaQuery = criteriaBuilder.createQuery(SectionEntity.class);
        Root<SectionEntity> root = criteriaQuery.from(SectionEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(SectionEntity_.statusEntity).get(StatusEntity_.code), statusCode)
        );

        try {
        	sysRoleEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
        }
        
        return sysRoleEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SectionEntity> criteriaQuery = criteriaBuilder.createQuery(SectionEntity.class);
        Root<SectionEntity> root = criteriaQuery.from(SectionEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(),criteriaBuilder,root);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.notEqual(root.get(SectionEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode()),
                		criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
        		)
        		
        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));
        

        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<SectionEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }	
	}
	
	private List<Predicate> createSearchPredicate(String searchValue,CriteriaBuilder cb,Root<SectionEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!searchValue.isEmpty()) {
			predicates.add(cb.like(root.get(SectionEntity_.code),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(SectionEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(SectionEntity_.statusEntity).get(StatusEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(SectionEntity_.createdBy),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(SectionEntity_.updatedBy),"%"+searchValue+"%"));
		}		
		else {
			predicates.add(cb.conjunction());
		}
		return predicates;
	}
	
	private List<Order> createSortOrder(String sortColumnName,String sortOrder, CriteriaBuilder cb,Root<SectionEntity> root){
		List<Order> orders = new ArrayList<>();		

		Expression<?> ex = root.get(SysRoleEntity_.updatedOn);
		
		
		if("code".equals(sortColumnName)) {
			ex = root.get(SectionEntity_.code);
		}
		else if("description".equals(sortColumnName)) {
			ex = root.get(SectionEntity_.description);
		}
		else if("status".equals(sortColumnName)) {
			ex = root.get(SectionEntity_.statusEntity).get(StatusEntity_.description);
		}
		else if("createdBy".equals(sortColumnName)) {
			ex = root.get(SectionEntity_.createdBy);
		}
		else if("createdOn".equals(sortColumnName)) {
			ex = root.get(SectionEntity_.createdOn);
		}
		else if("updatedBy".equals(sortColumnName)) {
			ex = root.get(SectionEntity_.updatedBy);
		}		
		
		orders.add(("asc".equals(sortOrder))? cb.asc(ex):cb.desc(ex));		
		
		return orders;
	}

}
