package com.leaf.eexamen.service;

import java.util.HashMap;

import com.leaf.eexamen.dto.SectionDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

public interface SectionService {
	/**
	 * Save Section
	 * @param sectionDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SectionDTO> saveSection(SectionDTO sectionDTO);
	
	/**
	 * Update Section
	 * @param sectionDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SectionDTO> updateSection(SectionDTO sectionDTO);
	
	/**
	 * Delete Section
	 * @param sectionDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SectionDTO> deleteSection(SectionDTO sectionDTO);
	
	/**
	 * Find Section By Code
	 * @param sectionDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<SectionDTO> findSection(SectionDTO sectionDTO);
	
	/**
	 * Load Reference Data For Section Page
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForSection();
	
	/**
	 * Get Sections Data For Data Table
	 * @param dataTableRequestDTO
	 * @return {@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getSectionsForDataTable(DataTableRequestDTO dataTableRequestDTO);
}
