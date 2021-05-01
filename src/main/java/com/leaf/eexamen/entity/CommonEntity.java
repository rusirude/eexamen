package com.leaf.eexamen.entity;



import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
@MappedSuperclass
public class CommonEntity {

    @Column(name = "created_by", nullable = false, length = 25)
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    private Date createdOn;
    @Column(name = "updated_by", nullable = false, length = 25)
    private String updatedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", nullable = false)
    private Date updatedOn;
}
