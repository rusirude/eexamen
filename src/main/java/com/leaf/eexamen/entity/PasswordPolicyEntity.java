package com.leaf.eexamen.entity;

import javax.persistence.*;

/**
 * @author : rusiru on 7/13/19.
 */

@Entity
@Table(name = "password_policy")
public class PasswordPolicyEntity extends CommonEntity {

    private Long id;
    private Integer minLength;
    private Integer maxLength;
    private Integer minChar;
    private Integer minNum;
    private Integer minSpeChar;
    private Integer noOfInvalidAttempt;
    private Integer noHistoryPassword;
    private StatusEntity statusEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "min_length", nullable = false)
    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    @Column(name = "max_length", nullable = false)
    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Column(name = "min_char", nullable = false)
    public Integer getMinChar() {
        return minChar;
    }

    public void setMinChar(Integer minChar) {
        this.minChar = minChar;
    }

    @Column(name = "min_num", nullable = false)
    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    @Column(name = "min_spe_char", nullable = false)
    public Integer getMinSpeChar() {
        return minSpeChar;
    }

    public void setMinSpeChar(Integer minSpeChar) {
        this.minSpeChar = minSpeChar;
    }

    @Column(name = "no_of_invalid_attempt", nullable = false)
    public Integer getNoOfInvalidAttempt() {
        return noOfInvalidAttempt;
    }

    public void setNoOfInvalidAttempt(Integer noOfInvalidAttempt) {
        this.noOfInvalidAttempt = noOfInvalidAttempt;
    }

    @Column(name = "no_of_history_password", nullable = false)
    public Integer getNoHistoryPassword() {
        return noHistoryPassword;
    }

    public void setNoHistoryPassword(Integer noHistoryPassword) {
        this.noHistoryPassword = noHistoryPassword;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "status" , nullable = false)
    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }
}
