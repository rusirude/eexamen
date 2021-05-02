package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@Table(name = "email_body")
public class EmailBodyEntity extends CommonEntity {
    @Id
    @Column(name = "code", length = 20, nullable = false, unique = true)
    private String code;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;
    @Column(name = "enable")
    private Boolean enable;
}
