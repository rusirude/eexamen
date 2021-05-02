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
@Table(name = "master_data")
public class MasterDataEntity extends CommonEntity {
    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "value", nullable = true)
    private String value;
}
