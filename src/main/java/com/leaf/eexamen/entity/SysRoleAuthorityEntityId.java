package com.leaf.eexamen.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class SysRoleAuthorityEntityId implements Serializable {

    @Column(name = "sys_role", nullable = false)
    private Long sysRole;
    @Column(name = "authority", nullable = false)
    private Long authority;
}
