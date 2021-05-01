package com.leaf.eexamen.dto;

import lombok.Data;

@Data
public class EmailBodyDTO {
    private String code;
    private String subject;
    private String content;
    private Boolean enable;
}
