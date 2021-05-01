package com.leaf.eexamen.entity;

import javax.persistence.*;

@Entity
@Table(name = "question_question_category")
public class QuestionQuestionCategoryEntity {
	
	private QuestionQuestionCategoryEntityId questionQuestionCategoryEntityId;
    private QuestionEntity questionEntity;
    private QuestionCategoryEntity questionCategoryEntity;
    

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "question", column = @Column(name = "question", nullable = false)),
			@AttributeOverride(name = "questionCategory", column = @Column(name = "question_category", nullable = false))
	})
	public QuestionQuestionCategoryEntityId getQuestionQuestionCategoryEntityId() {
		return questionQuestionCategoryEntityId;
	}

	public void setQuestionQuestionCategoryEntityId(QuestionQuestionCategoryEntityId questionQuestionCategoryEntityId) {
		this.questionQuestionCategoryEntityId = questionQuestionCategoryEntityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question", referencedColumnName = "id", insertable = false, updatable = false)
	public QuestionEntity getQuestionEntity() {
		return questionEntity;
	}

	public void setQuestionEntity(QuestionEntity questionEntity) {
		this.questionEntity = questionEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_category", referencedColumnName = "id", insertable = false, updatable = false)
	public QuestionCategoryEntity getQuestionCategoryEntity() {
		return questionCategoryEntity;
	}

	public void setQuestionCategoryEntity(QuestionCategoryEntity questionCategoryEntity) {
		this.questionCategoryEntity = questionCategoryEntity;
	}
}
