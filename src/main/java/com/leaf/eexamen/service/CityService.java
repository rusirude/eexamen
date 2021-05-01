package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.CityDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.HashMap;

public interface CityService {
	/**
	 * Save City
	 * @param cityDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<CityDTO> saveCity(CityDTO cityDTO);
	
	/**
	 * Update City
	 * @param cityDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<CityDTO> updateCity(CityDTO cityDTO);
	
	/**
	 * Delete City
	 * @param cityDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<CityDTO> deleteCity(CityDTO cityDTO);
	
	/**
	 * Find City By Code
	 * @param cityDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<CityDTO> findCity(CityDTO cityDTO);
	
	/**
	 * Load Reference Data For City Page
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForCity();
	
	/**
	 * Get Countries Data For Data Table
	 * @param dataTableRequestDTO
	 * @return {@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getCitysForDataTable(DataTableRequestDTO dataTableRequestDTO);
}
