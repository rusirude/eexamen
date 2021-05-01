package com.leaf.eexamen.dao;

import com.leaf.eexamen.entity.StatusCategoryEntity;

public interface StatusCategoryDAO {

	/**
	 * Find Status Category By Code
	 * @param code
	 * @return {@link StatusCategoryEntity}
	 */
	StatusCategoryEntity findStatusCategoryByCode(String code);
}
