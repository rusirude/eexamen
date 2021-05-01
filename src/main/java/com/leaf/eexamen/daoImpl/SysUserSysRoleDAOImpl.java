package com.leaf.eexamen.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.leaf.eexamen.dao.SysUserSysRoleDAO;
import com.leaf.eexamen.entity.StatusEntity_;
import com.leaf.eexamen.entity.SysRoleEntity_;
import com.leaf.eexamen.entity.SysUserEntity_;
import com.leaf.eexamen.entity.SysUserSysRoleEntity;
import com.leaf.eexamen.entity.SysUserSysRoleEntityId_;
import com.leaf.eexamen.entity.SysUserSysRoleEntity_;
import com.leaf.eexamen.enums.DefaultStatusEnum;

@Repository
public class SysUserSysRoleDAOImpl implements SysUserSysRoleDAO {
	
	@Autowired
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SysUserSysRoleEntity> getSysUserSysRoleEntitiesBySysUser(String username) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SysUserSysRoleEntity> criteriaQuery = criteriaBuilder.createQuery(SysUserSysRoleEntity.class);
        Root<SysUserSysRoleEntity> root = criteriaQuery.from(SysUserSysRoleEntity.class);
        criteriaQuery.select(root);
        criteriaQuery.where(
        		criteriaBuilder.and(
        				criteriaBuilder.equal(root.get(SysUserSysRoleEntity_.sysRoleEntity).get(SysRoleEntity_.statusEntity).get(StatusEntity_.code),DefaultStatusEnum.ACTIVE.getCode()),
        				criteriaBuilder.equal(root.get(SysUserSysRoleEntity_.sysUserSysRoleEntityId).get(SysUserSysRoleEntityId_.sysUser),username)
        				));
        return entityManager.createQuery(criteriaQuery).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteSysUserSysRoleEntityBySysRole(long sysRoleId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<SysUserSysRoleEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(SysUserSysRoleEntity.class);
        Root<SysUserSysRoleEntity> root = criteriaDelete.from(SysUserSysRoleEntity.class);        
        criteriaDelete.where(
        		criteriaBuilder.equal(root.get(SysUserSysRoleEntity_.sysRoleEntity).get(SysRoleEntity_.id),sysRoleId)     		
		);
        
        entityManager.createQuery(criteriaDelete).executeUpdate();	

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteSysUserSysRoleEntityBySysUser(String username) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<SysUserSysRoleEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(SysUserSysRoleEntity.class);
        Root<SysUserSysRoleEntity> root = criteriaDelete.from(SysUserSysRoleEntity.class);        
        criteriaDelete.where(
        		criteriaBuilder.equal(root.get(SysUserSysRoleEntity_.sysUserEntity).get(SysUserEntity_.username),username)     		
		);
        
        entityManager.createQuery(criteriaDelete).executeUpdate();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteSysUserSysRoleEntityBySysUserAndSysRole(String username, long sysRoleId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<SysUserSysRoleEntity> criteriaDelete = criteriaBuilder.createCriteriaDelete(SysUserSysRoleEntity.class);
        Root<SysUserSysRoleEntity> root = criteriaDelete.from(SysUserSysRoleEntity.class);        
        criteriaDelete.where(
        		criteriaBuilder.and(
        				criteriaBuilder.equal(root.get(SysUserSysRoleEntity_.sysUserEntity).get(SysUserEntity_.username),username),   
        				criteriaBuilder.equal(root.get(SysUserSysRoleEntity_.sysRoleEntity).get(SysRoleEntity_.id), sysRoleId)
        				)
        		);        
        entityManager.createQuery(criteriaDelete).executeUpdate();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveSysUserSysRoleEntity(SysUserSysRoleEntity sysUserSysRoleEntity) {
		entityManager.persist(sysUserSysRoleEntity);

	}

}
