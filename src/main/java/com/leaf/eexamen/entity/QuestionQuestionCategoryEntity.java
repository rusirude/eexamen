package com.leaf.eexamen.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "question_question_category")
public class QuestionQuestionCategoryEntity {

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "question", column = @Column(name = "question", nullable = false)),
			@AttributeOverride(name = "questionCategory", column = @Column(name = "question_category", nullable = false))
	})
	private QuestionQuestionCategoryEntityId questionQuestionCategoryEntityId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question", referencedColumnName = "id", insertable = false, updatable = false)
	private QuestionEntity questionEntity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_category", referencedColumnName = "id", insertable = false, updatable = false)
	private QuestionCategoryEntity questionCategoryEntity;
}
