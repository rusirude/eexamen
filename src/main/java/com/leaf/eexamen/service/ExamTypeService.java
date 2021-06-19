package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.ExamTypeDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.HashMap;

public interface ExamTypeService {
	/**
	 * Save Question
	 * @param examTypeDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExamTypeDTO> saveExamType(ExamTypeDTO examTypeDTO);
	
	/**
	 * Update ExamType
	 * @param examTypeDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExamTypeDTO> updateExamType(ExamTypeDTO examTypeDTO);
	
	/**
	 * Delete ExamType
	 * @param examTypeDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExamTypeDTO> deleteExamType(ExamTypeDTO examTypeDTO);
	
	/**
	 * Find ExamType By Code
	 * @param examTypeDTO - code
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<ExamTypeDTO> findExamType(ExamTypeDTO examTypeDTO);
	
	/**
	 * Load Reference Data For ExamType Page
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForExamType();
	
	/**
	 * Get ExamTypes Data For Data Table
	 * @param dataTableRequestDTO
	 * @return {@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getExamTypesForDataTable(DataTableRequestDTO dataTableRequestDTO);
}
