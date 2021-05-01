package com.leaf.eexamen.dto.common;

import lombok.Data;

@Data
public class DataTableRequestDTO {
	private Integer draw;
    private Integer start;
    private Integer length;
    private String sortColumnName;
    private String sortOrder;
    private String search;

}
