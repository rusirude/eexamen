package com.leaf.eexamen.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="student_examination")
public class StudentExaminationEntity extends CommonEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student", nullable = false)
    private SysUserEntity sysUserEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination", nullable = false)
    private ExaminationEntity examinationEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "status" , nullable = false)
    private StatusEntity statusEntity;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_on")
    private Date startOn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_on")
    private Date endOn;
    @Column(name = "final_mark")
    private Double finalMark;
    @Column(name = "is_pass")
    private Boolean pass;
    @Column(name = "pass_mark")
    private Double passMark;
}