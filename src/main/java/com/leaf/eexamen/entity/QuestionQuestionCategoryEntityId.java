package com.leaf.eexamen.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuestionQuestionCategoryEntityId implements Serializable{
	

	private Long question;
	private Long questionCategory;


	@Column(name = "question", nullable = false)
	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	@Column(name = "question_category", nullable = false)
	public Long getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(Long questionCategory) {
		this.questionCategory = questionCategory;
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionCategory, question);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionQuestionCategoryEntityId other = (QuestionQuestionCategoryEntityId) obj;
		return Objects.equals(questionCategory, other.questionCategory) && Objects.equals(question, other.question);
	}
	
		 
	 
}
