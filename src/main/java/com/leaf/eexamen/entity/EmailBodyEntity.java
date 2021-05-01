package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
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
