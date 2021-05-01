package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.StudentDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.HashMap;

public interface StudentService {
	
	
	/**
	 * Save System User
	 * @param studentDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<StudentDTO> saveStudent(StudentDTO studentDTO);
	
	/**
	 * Update System User
	 * @param studentDTO
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<StudentDTO> updateStudent(StudentDTO studentDTO);
	
	/**
	 * Delete System User
	 * @param studentDTO - username
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<StudentDTO> deleteStudent(StudentDTO studentDTO);
	
	/**
	 * Find System User By username
	 * @param studentDTO - username
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<StudentDTO> findStudent(StudentDTO studentDTO);

	/**
	 * Load Reference Data For Student Page
	 * @return {@link ResponseDTO}
	 */
	ResponseDTO<HashMap<String, Object>> getReferenceDataForStudent();	
	
	/**
	 * Get System Users Data For Data Table
	 * @param dataTableRequestDTO
	 * @return {@link DataTableResponseDTO}
	 */
	DataTableResponseDTO getStudentsForDataTable(DataTableRequestDTO dataTableRequestDTO);
	
}
