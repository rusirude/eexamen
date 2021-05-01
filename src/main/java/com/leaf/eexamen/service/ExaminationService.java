package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.ExaminationDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.HashMap;

public interface ExaminationService {
	/**
	 * Save Examination
	 * @param examinationDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExaminationDTO> saveExamination(ExaminationDTO examinationDTO);
	
	/**
	 * Update Examination
	 * @param examinationDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExaminationDTO> updateExamination(ExaminationDTO examinationDTO);
	
	/**
	 * Delete Examination
	 * @param examinationDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExaminationDTO> deleteExamination(ExaminationDTO examinationDTO);
	
	/**
	 * Find Examination By Code
	 * @param examinationDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExaminationDTO> findExamination(ExaminationDTO examinationDTO);
	
	/**
	 * Load Reference Data For Examination Page
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForExamination();
	
	/**
	 * Get Countries Data For Data Table
	 * @param dataTableRequestDTO
	 * @return {@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getExaminationsForDataTable(DataTableRequestDTO dataTableRequestDTO);
}
