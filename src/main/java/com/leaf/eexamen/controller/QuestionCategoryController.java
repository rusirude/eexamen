package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.QuestionCategoryDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.QuestionCategoryService;
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
@RequestMapping(path = "/questionCategory")
public class QuestionCategoryController {

	private QuestionCategoryService questionCategoryService;

	@Autowired
	public QuestionCategoryController(QuestionCategoryService questionCategoryService) {
		this.questionCategoryService = questionCategoryService;
	}

	@PreAuthorize("hasRole('ROLE_QUECATEGORY')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewQuestionCategory() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("questionCategory");
		return mv;
	}

	@PreAuthorize("hasRole('ROLE_QUECATEGORY')")
	@RequestMapping(path = "/loadRefDataForQuestionCategory", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadQuestionCategoryReferenceData() {
		return questionCategoryService.getReferenceDataForQuestionCategory();
	}

	@PreAuthorize("hasRole('ROLE_QUECATEGORY')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveQuestionCategory(@RequestBody QuestionCategoryDTO questionCategoryDTO) {
		return questionCategoryService.saveQuestionCategory(questionCategoryDTO);
	}

	@PreAuthorize("hasRole('ROLE_QUECATEGORY')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateQuestionCategory(@RequestBody QuestionCategoryDTO questionCategoryDTO) {
		return questionCategoryService.updateQuestionCategory(questionCategoryDTO);
	}

	@PreAuthorize("hasRole('ROLE_QUECATEGORY')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> deleteQuestionCategory(@RequestBody QuestionCategoryDTO questionCategoryDTO) {
		return questionCategoryService.deleteQuestionCategory(questionCategoryDTO);
	}

	@PreAuthorize("hasRole('ROLE_QUECATEGORY')")
	@RequestMapping(path = "/loadQuestionCategories", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadQuestionCategoryDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return questionCategoryService.getQuestionCategoriesForDataTable(dataTableRequestDTO);
	}

	@PreAuthorize("hasRole('ROLE_QUECATEGORY')")
	@RequestMapping(path = "/loadQuestionCategoryByCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<QuestionCategoryDTO> loadQuestionCategoryByCode(@RequestBody QuestionCategoryDTO questionCategoryDTO) {
		return questionCategoryService.findQuestionCategory(questionCategoryDTO);
	}
}
