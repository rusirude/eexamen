package com.leaf.eexamen.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.entity.StatusEntity_;

@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatusDAOImpl implements StatusDAO {
	

	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StatusEntity findStatusEntityByCode(String code) {
		StatusEntity statusEntity = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatusEntity> criteriaQuery = criteriaBuilder.createQuery(StatusEntity.class);
        Root<StatusEntity> root = criteriaQuery.from(StatusEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.equal(root.get(StatusEntity_.code), code)
        );

        try {
        	statusEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return statusEntity;
	}

}
