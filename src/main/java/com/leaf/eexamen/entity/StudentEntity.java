package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="student")
public class StudentEntity extends CommonEntity{
    @Id
    @Column(name  = "username", length = 25 , nullable = false , unique = true)
    private String username;
    @Column(name = "initial_password")
    private String initialPassword;
    @Column(name  = "email")
    private String email;
    @Column(name  = "telephone")
    private String telephone;
    @Column(name  = "address")
    private String address;
    @Column(name  = "company")
    private String company;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "city" , nullable = false)
    private CityEntity cityEntity;
    @Column(name  = "zip_code")
    private String zipCode;
    @Column(name  = "vat")
    private String vat;
}