package com.leaf.eexamen.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.entity.StatusCategoryEntity;
import com.leaf.eexamen.entity.StatusCategoryEntity_;

@Repository
public class StatusCategoryDAOImpl implements StatusCategoryDAO {

	@Autowired
	private EntityManager entityManager;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatusCategoryEntity findStatusCategoryByCode(String code) {
		StatusCategoryEntity statusCategoryEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatusCategoryEntity> criteriaQuery = criteriaBuilder.createQuery(StatusCategoryEntity.class);
        Root<StatusCategoryEntity> root = criteriaQuery.from(StatusCategoryEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(StatusCategoryEntity_.code), code)
        );

        try {
        	statusCategoryEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
        }

        return statusCategoryEntity;
	}

}
