package com.leaf.eexamen.dao;

import com.leaf.eexamen.entity.StatusEntity;

public interface StatusDAO {
	
	/**
	 * Find Status Entity By Code
	 * @param code
	 * @return {@link StatusEntity}
	 */
	StatusEntity findStatusEntityByCode(String code);
}
