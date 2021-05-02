package com.leaf.eexamen.daoImpl;

import com.leaf.eexamen.dao.SysUserAuthorityDAO;
import com.leaf.eexamen.entity.*;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserAuthorityDAOImpl implements SysUserAuthorityDAO {
	
	

	private EntityManager entityManager;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SysUserAuthorityEntity> getSysUserAuthorityEntitiesBySysUser(String username){
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUserAuthorityEntity> criteriaQuery = criteriaBuilder.createQuery(SysUserAuthorityEntity.class);
        Root<SysUserAuthorityEntity> root = criteriaQuery.from(SysUserAuthorityEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.equal(root.get(SysUserAuthorityEntity_.authorityEntity).get(AuthorityEntity_.statusEntity).get(StatusEntity_.code),DefaultStatusEnum.ACTIVE.getCode()),
        				criteriaBuilder.equal(root.get(SysUserAuthorityEntity_.sysUserEntity).get(SysUserEntity_.username),username)        		     
        				)
    		);
        return entityManager.createQuery(criteriaQuery).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteSysUserAuthorityEntityByAuthority(long authorityId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<SysUserAuthorityEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(SysUserAuthorityEntity.class);
        Root<SysUserAuthorityEntity> root = criteriaDelete.from(SysUserAuthorityEntity.class);        
        criteriaDelete.where(
        		criteriaBuilder.equal(root.get(SysUserAuthorityEntity_.authorityEntity).get(AuthorityEntity_.id), authorityId)    		
		);
        
        entityManager.createQuery(criteriaDelete).executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteSysUserAuthorityEntityBySysUser(String username) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<SysUserAuthorityEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(SysUserAuthorityEntity.class);
        Root<SysUserAuthorityEntity> root = criteriaDelete.from(SysUserAuthorityEntity.class);        
        criteriaDelete.where(
        		criteriaBuilder.equal(root.get(SysUserAuthorityEntity_.sysUserEntity).get(SysUserEntity_.username),username)     		
		);
        
        entityManager.createQuery(criteriaDelete).executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteSysUserAuthorityEntityBySysUserAndAuthority(String username, long authorityId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<SysUserAuthorityEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(SysUserAuthorityEntity.class);
        Root<SysUserAuthorityEntity> root = criteriaDelete.from(SysUserAuthorityEntity.class);        
        criteriaDelete.where(
        		criteriaBuilder.and(
        				criteriaBuilder.equal(root.get(SysUserAuthorityEntity_.sysUserEntity).get(SysUserEntity_.username),username),   
        				criteriaBuilder.equal(root.get(SysUserAuthorityEntity_.authorityEntity).get(AuthorityEntity_.id), authorityId)
        				)
        		);        
        entityManager.createQuery(criteriaDelete).executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveSysUserAuthorityEntity(SysUserAuthorityEntity sysUserAuthorityEntity) {
		entityManager.persist(sysUserAuthorityEntity);		
	}

}
