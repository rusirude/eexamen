package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@EqualsAndHashCode
@Embeddable
public class QuestionQuestionCategoryEntityId implements Serializable{
	

	@Column(name = "question", nullable = false)
	private Long question;
	@Column(name = "question_category", nullable = false)
	private Long questionCategory;
}
