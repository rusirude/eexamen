package com.leaf.eexamen.dto;

import com.leaf.eexamen.dto.common.CommonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionDTO extends CommonDTO{

	private String code;
	private String description;
	private String statusCode;
	private String statusDescription;
	private List<QuestionCategoryDTO> questionCategories;
	private List<QuestionAnswerDTO> questionAnswers;
}
