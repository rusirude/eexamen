package com.leaf.eexamen.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class QuestionQuestionCategoryEntityId implements Serializable {


    @Column(name = "question", nullable = false)
    private Long question;
    @Column(name = "question_category", nullable = false)
    private Long questionCategory;
}
