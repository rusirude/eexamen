package com.leaf.eexamen.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.leaf.eexamen.dao.MasterDataDAO;
import com.leaf.eexamen.entity.MasterDataEntity;

@Repository
public class MasterDataDAOImpl implements MasterDataDAO {

	private EntityManager entityManager;	
	
	@Autowired
	public MasterDataDAOImpl(EntityManager entityManager) {		
		this.entityManager = entityManager;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MasterDataEntity loadMasterDataEntity(String code) {		
		return entityManager.getReference(MasterDataEntity.class, code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MasterDataEntity findMasterDataEntity(String code) {		
		return entityManager.find(MasterDataEntity.class, code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMasterDataEntity(MasterDataEntity masterDataEntity) {
		entityManager.persist(masterDataEntity);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateMasterDataEntity(MasterDataEntity masterDataEntity) {
		entityManager.merge(masterDataEntity);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MasterDataEntity> findAllMasterDataEntities() {
		List<MasterDataEntity> masterDAtaEntities = null;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MasterDataEntity> criteriaQuery = criteriaBuilder.createQuery(MasterDataEntity.class);
        Root<MasterDataEntity> root = criteriaQuery.from(MasterDataEntity.class);
        criteriaQuery.select(root);
        try {
        	masterDAtaEntities = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return masterDAtaEntities;
	}

}
