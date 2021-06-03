package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.QuestionDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.QuestionService;
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

	private QuestionService questionService;

	@Autowired
	public ExamTypeController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@PreAuthorize("hasRole('ROLE_EXMTYP')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewQuestion() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("examType");
		return mv;
	}

//	@PreAuthorize("hasRole('ROLE_QUS')")
//	@RequestMapping(path = "/loadRefDataForQuestion", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseDTO<HashMap<String, Object>> loadQuestionReferenceData() {
//		return questionService.getReferenceDataForQuestion();
//	}
//
//	@PreAuthorize("hasRole('ROLE_QUS')")
//	@RequestMapping(path = "/save", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseDTO<?> saveQuestion(@RequestBody QuestionDTO questionDTO) {
//		return questionService.saveQuestion(questionDTO);
//	}
//
//	@PreAuthorize("hasRole('ROLE_QUS')")
//	@RequestMapping(path = "/update", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseDTO<?> updateQuestion(@RequestBody QuestionDTO questionDTO) {
//		return questionService.updateQuestion(questionDTO);
//	}
//
//	@PreAuthorize("hasRole('ROLE_QUS')")
//	@RequestMapping(path = "/delete", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseDTO<?> deleteQuestion(@RequestBody QuestionDTO questionDTO) {
//		return questionService.deleteQuestion(questionDTO);
//	}
//
//	@PreAuthorize("hasRole('ROLE_QUS')")
//	@RequestMapping(path = "/loadQuestions", method = RequestMethod.POST)
//	@ResponseBody
//	public DataTableResponseDTO loadQuestionDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
//		return questionService.getQuestionsForDataTable(dataTableRequestDTO);
//	}
//
//	@PreAuthorize("hasRole('ROLE_QUS')")
//	@RequestMapping(path = "/loadQuestionByCode", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseDTO<QuestionDTO> loadQuestionByCode(@RequestBody QuestionDTO questionDTO) {
//		return questionService.findQuestion(questionDTO);
//	}
}
