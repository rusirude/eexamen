package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.CityDAO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.CityEntity;
import com.leaf.eexamen.entity.StatusEntity_;
import com.leaf.eexamen.entity.SysRoleEntity_;
import com.leaf.eexamen.entity.CityEntity_;
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
public class CityDAOImpl implements CityDAO {

	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CityEntity loadCityEntity(long id) {
		return entityManager.getReference(CityEntity.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CityEntity findCityEntity(long id) {
		return entityManager.find(CityEntity.class,id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CityEntity findCityEntityByCode(String code) {
		CityEntity CityEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityEntity> criteriaQuery = criteriaBuilder.createQuery(CityEntity.class);
        Root<CityEntity> root = criteriaQuery.from(CityEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
                criteriaBuilder.and(
                      criteriaBuilder.equal(root.get(CityEntity_.code), code),
                      criteriaBuilder.notEqual(root.get(CityEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
                )
        );

        try {
            CityEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
			log.info(e.getMessage());
        }

        return CityEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveCityEntity(CityEntity cityEntity) {
		entityManager.persist(cityEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateCityEntity(CityEntity cityEntity) {
		entityManager.merge(cityEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CityEntity> findAllCityEntities() {
		List<CityEntity> cityEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityEntity> criteriaQuery = criteriaBuilder.createQuery(CityEntity.class);
        Root<CityEntity> root = criteriaQuery.from(CityEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.notEqual(root.get(CityEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode())
        );

        try {
        	cityEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
        }
        
        return cityEntities;
	}
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CityEntity> findAllCityEntities(String statusCode){
		List<CityEntity> cityEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityEntity> criteriaQuery = criteriaBuilder.createQuery(CityEntity.class);
        Root<CityEntity> root = criteriaQuery.from(CityEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(CityEntity_.statusEntity).get(StatusEntity_.code), statusCode)
        );

        try {
			cityEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
			log.info(e.getMessage());
        }
        
        return cityEntities;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityEntity> criteriaQuery = criteriaBuilder.createQuery(CityEntity.class);
        Root<CityEntity> root = criteriaQuery.from(CityEntity.class);
        List<Predicate> predicates = createSearchPredicate(dataTableRequestDTO.getSearch(),criteriaBuilder,root);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.notEqual(root.get(CityEntity_.statusEntity).get(StatusEntity_.code), DeleteStatusEnum.DELETE.getCode()),
                		criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]))
        		)
        		
        );

        criteriaQuery.orderBy(createSortOrder(dataTableRequestDTO.getSortColumnName(), dataTableRequestDTO.getSortOrder(), criteriaBuilder, root));
        

        if (CommonConstant.GRID_SEARC_COUNT.equals(type)) {
            long count = entityManager.createQuery(criteriaQuery).getResultList().size();
            return (T) Long.valueOf(count);
        } else {
            TypedQuery<CityEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(dataTableRequestDTO.getStart());
            typedQuery.setMaxResults(dataTableRequestDTO.getLength());
            return (T) typedQuery.getResultList();
        }	
	}
	
	private List<Predicate> createSearchPredicate(String searchValue,CriteriaBuilder cb,Root<CityEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!searchValue.isEmpty()) {
			predicates.add(cb.like(root.get(CityEntity_.code),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(CityEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(CityEntity_.statusEntity).get(StatusEntity_.description),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(CityEntity_.createdBy),"%"+searchValue+"%"));
			predicates.add(cb.like(root.get(CityEntity_.updatedBy),"%"+searchValue+"%"));
		}		
		else {
			predicates.add(cb.conjunction());
		}
		return predicates;
	}
	
	private List<Order> createSortOrder(String sortColumnName,String sortOrder, CriteriaBuilder cb,Root<CityEntity> root){
		List<Order> orders = new ArrayList<>();		

		Expression<?> ex = root.get(SysRoleEntity_.updatedOn);
		
		
		if("code".equals(sortColumnName)) {
			ex = root.get(CityEntity_.code);
		}
		else if("description".equals(sortColumnName)) {
			ex = root.get(CityEntity_.description);
		}
		else if("status".equals(sortColumnName)) {
			ex = root.get(CityEntity_.statusEntity).get(StatusEntity_.description);
		}
		else if("createdBy".equals(sortColumnName)) {
			ex = root.get(CityEntity_.createdBy);
		}
		else if("createdOn".equals(sortColumnName)) {
			ex = root.get(CityEntity_.createdOn);
		}
		else if("updatedBy".equals(sortColumnName)) {
			ex = root.get(CityEntity_.updatedBy);
		}		
		
		orders.add(("asc".equals(sortOrder))? cb.asc(ex):cb.desc(ex));		
		
		return orders;
	}

}
