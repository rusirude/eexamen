package com.leaf.eexamen.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="student_examination")
public class StudentExaminationEntity extends CommonEntity{

    private Long id;
    private SysUserEntity sysUserEntity;
    private ExaminationEntity examinationEntity;
    private StatusEntity statusEntity;
    private Date startOn;
    private Date endOn;
    private Double finalMark;
    private Boolean pass;
    private Double passMark;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student", nullable = false)
    public SysUserEntity getSysUserEntity() {
        return sysUserEntity;
    }

    public void setSysUserEntity(SysUserEntity sysUserEntity) {
        this.sysUserEntity = sysUserEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination", nullable = false)
    public ExaminationEntity getExaminationEntity() {
        return examinationEntity;
    }

    public void setExaminationEntity(ExaminationEntity examinationEntity) {
        this.examinationEntity = examinationEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "status" , nullable = false)
    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_on")
    public Date getStartOn() {
        return startOn;
    }

    public void setStartOn(Date startOn) {
        this.startOn = startOn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_on")
    public Date getEndOn() {
        return endOn;
    }

    public void setEndOn(Date endOn) {
        this.endOn = endOn;
    }

    public Double getFinalMark() {
        return finalMark;
    }

    @Column(name = "final_mark")
    public void setFinalMark(Double finalMark) {
        this.finalMark = finalMark;
    }

    @Column(name = "is_pass")
    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    @Column(name = "pass_mark")
    public Double getPassMark() {
        return passMark;
    }

    public void setPassMark(Double passMark) {
        this.passMark = passMark;
    }
}