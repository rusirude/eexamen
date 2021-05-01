package com.leaf.eexamen.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDTO extends SysUserDTO {
    private String email;
    private String telephone;
    private String address;
    private String company;
    private String examCode;
    private String zipCode;
    private String cityCode;
    private String cityDescription;
    private String vat;
}