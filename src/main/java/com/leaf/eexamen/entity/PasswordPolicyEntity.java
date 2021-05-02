package com.leaf.eexamen.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author : rusiru on 7/13/19.
 */


@Getter
@Setter
@Entity
@Table(name = "password_policy")
public class PasswordPolicyEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "min_length", nullable = false)
    private Integer minLength;
    @Column(name = "max_length", nullable = false)
    private Integer maxLength;
    @Column(name = "min_char", nullable = false)
    private Integer minChar;
    @Column(name = "min_num", nullable = false)
    private Integer minNum;
    @Column(name = "min_spe_char", nullable = false)
    private Integer minSpeChar;
    @Column(name = "no_of_invalid_attempt", nullable = false)
    private Integer noOfInvalidAttempt;
    @Column(name = "no_of_history_password", nullable = false)
    private Integer noHistoryPassword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private StatusEntity statusEntity;
}
