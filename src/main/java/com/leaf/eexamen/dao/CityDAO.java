package com.leaf.eexamen.dao;

import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.entity.CityEntity;

import java.util.List;

public interface CityDAO {
	/**
	 * Load Reference Entity
	 * @param id
	 * @return {@link CityEntity}
	 */
    CityEntity loadCityEntity(long id);

	/**
	 * Find Entity from DB By Id
	 * @param id
	 * @return {@link CityEntity}
	 */
    CityEntity findCityEntity(long id);

	/**
	 * Find Entity from DB By Code
	 * @param code
	 * @return {@link CityEntity}
	 */
    CityEntity findCityEntityByCode(String code);
    
    /**
     * Save City Entity
     * @param cityEntity
     */
    void saveCityEntity(CityEntity cityEntity);
    
    /**
     * Update City Entity
     * @param cityEntity
     */
    void updateCityEntity(CityEntity cityEntity);
    
    /**
     * select - *
     * From - City
     * where - STATUS <> DELETE
     * 
     * Find City Entities without delete
     * @return {@link List}
     */
    List<CityEntity> findAllCityEntities();
    
    
    /**
     * select - *
     * From - City
     * where - STATUS = statusCode
     * 
     * Find City Entities By Status Code
     * @return {@link List}
     */
    List<CityEntity> findAllCityEntities(String statusCode);
    
    /**
     * Getting Data For  Grid 
     * @param dataTableRequestDTO
     * @return 
     */
    <T> T getDataForGrid(DataTableRequestDTO dataTableRequestDTO, String type);
}
