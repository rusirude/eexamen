package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamTypeDTO extends CommonDTO{

	private String code;
	private String description;
	private String statusCode;
	private String statusDescription;
	private String examCategoryCode;
	private String examCategoryDescription;
	private String questionCategoryCode;
	private String questionCategoryDescription;
	private Double tPassMark;
	private Double wPassMark;
	private List<ExamTypeQuestionModelDTO> tQuestions;
	private List<ExamTypeQuestionModelDTO> wQuestions;
}
