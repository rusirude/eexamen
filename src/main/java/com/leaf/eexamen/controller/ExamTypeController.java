package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.ExamTypeDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.ExamTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping(path = "/examType")
public class ExamTypeController {

	private ExamTypeService examTypeService;

	@Autowired
	public ExamTypeController(ExamTypeService examTypeService) {
		this.examTypeService = examTypeService;
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewExamType() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("examType");
		return mv;
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/loadRefDataForExamType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadExamTypeReferenceData() {
		return examTypeService.getReferenceDataForExamType();
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveExamType(@RequestBody ExamTypeDTO examTypeDTO) {
		return examTypeService.saveExamType(examTypeDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateExamType(@RequestBody ExamTypeDTO examTypeDTO) {
		return examTypeService.updateExamType(examTypeDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> deleteExamType(@RequestBody ExamTypeDTO examTypeDTO) {
		return examTypeService.deleteExamType(examTypeDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/loadExamTypes", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadExamTypeDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return examTypeService.getExamTypesForDataTable(dataTableRequestDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/loadExamTypeByCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<ExamTypeDTO> loadExamTypeByCode(@RequestBody ExamTypeDTO examTypeDTO) {
		return examTypeService.findExamType(examTypeDTO);
	}
}
