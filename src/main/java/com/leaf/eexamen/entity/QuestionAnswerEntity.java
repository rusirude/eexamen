package com.leaf.eexamen.entity;

import javax.persistence.*;

@Entity
@Table(name = "question_answer")
public class QuestionAnswerEntity extends CommonEntity{
	
	private Long id;
	private String description;
    private StatusEntity statusEntity;
	private QuestionEntity questionEntity;
	private Integer position;
	private boolean correct;


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "description", length = 50, nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "status" , nullable = false)
	public StatusEntity getStatusEntity() {
		return statusEntity;
	}
	
	public void setStatusEntity(StatusEntity statusEntity) {
		this.statusEntity = statusEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name =  "question" , nullable = false)
	public QuestionEntity getQuestionEntity() {
		return questionEntity;
	}

	public void setQuestionEntity(QuestionEntity questionEntity) {
		this.questionEntity = questionEntity;
	}

	@Column(name = "position")
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Column(name = "correct")
	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}


}
