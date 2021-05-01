package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.ExaminationDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.ExaminationService;
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
@RequestMapping(path = "/examination")
public class ExaminationController {

	private ExaminationService examinationService;

	@Autowired
	public ExaminationController(ExaminationService examinationService) {
		this.examinationService = examinationService;
	}

	@PreAuthorize("hasRole('ROLE_EXAMMGT')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewExamination() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("examination");
		return mv;
	}

	@PreAuthorize("hasRole('ROLE_EXAMMGT')")
	@RequestMapping(path = "/loadRefDataForExamination", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadExaminationReferenceData() {
		return examinationService.getReferenceDataForExamination();
	}

	@PreAuthorize("hasRole('ROLE_EXAMMGT')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveExamination(@RequestBody ExaminationDTO examinationDTO) {
		return examinationService.saveExamination(examinationDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXAMMGT')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateExamination(@RequestBody ExaminationDTO examinationDTO) {
		return examinationService.updateExamination(examinationDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXAMMGT')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> deleteExamination(@RequestBody ExaminationDTO examinationDTO) {
		return examinationService.deleteExamination(examinationDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXAMMGT')")
	@RequestMapping(path = "/loadExaminations", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadExaminationDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return examinationService.getExaminationsForDataTable(dataTableRequestDTO);
	}

	@PreAuthorize("hasRole('ROLE_EXAMMGT')")
	@RequestMapping(path = "/loadExaminationByCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<ExaminationDTO> loadExaminationByCode(@RequestBody ExaminationDTO examinationDTO) {
		return examinationService.findExamination(examinationDTO);
	}
}
