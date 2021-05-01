package com.leaf.eexamen.entity;

import javax.persistence.*;

@Entity
@Table(name="student")
public class StudentEntity extends CommonEntity{
    private String username;
    private String initialPassword;
    private String email;
    private String telephone;
    private String address;
    private String company;
    private CityEntity cityEntity;
    private String zipCode;
    private String vat;

    @Id
    @Column(name  = "username", length = 25 , nullable = false , unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "initial_password")
    public String getInitialPassword() {
        return initialPassword;
    }

    public void setInitialPassword(String initialPassword) {
        this.initialPassword = initialPassword;
    }

    @Column(name  = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name  = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name  = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name  = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "city" , nullable = false)
    public CityEntity getCityEntity() {
        return cityEntity;
    }

    public void setCityEntity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    @Column(name  = "zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name  = "vat")
    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }
}