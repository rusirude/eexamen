package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;

import java.util.List;

public class QuestionDTO extends CommonDTO{

	private String code;
	private String description;
	private String statusCode;
	private String statusDescription;
	private List<QuestionCategoryDTO> questionCategories;
	private List<QuestionAnswerDTO> questionAnswers;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public List<QuestionCategoryDTO> getQuestionCategories() {
		return questionCategories;
	}

	public void setQuestionCategories(List<QuestionCategoryDTO> questionCategories) {
		this.questionCategories = questionCategories;
	}

	public List<QuestionAnswerDTO> getQuestionAnswers() {
		return questionAnswers;
	}
	public void setQuestionAnswers(List<QuestionAnswerDTO> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}
}
