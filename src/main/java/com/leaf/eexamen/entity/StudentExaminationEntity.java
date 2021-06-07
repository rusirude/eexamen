package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "student_examination")
public class StudentExaminationEntity extends CommonEntity {

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
    @JoinColumn(name = "status", nullable = false)
    private StatusEntity statusEntity;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_on")
    private Date startOn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_on")
    private Date endOn;
    @Column(name = "t_final_mark")
    private Double tFinalMark;
    @Column(name = "w_final_mark")
    private Double wFinalMark;
    @Column(name = "is_pass")
    private Boolean pass;
    @Column(name = "t_pass_mark")
    private Double tPassMark;
    @Column(name = "w_pass_mark")
    private Double wPassMark;
    @Column(name = "t_count")
    private Integer tCount;
    @Column(name = "w_count")
    private Integer wCount;
}