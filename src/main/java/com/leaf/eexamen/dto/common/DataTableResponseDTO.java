package com.leaf.eexamen.dto.common;

import lombok.Data;

import java.util.List;

@Data
public class DataTableResponseDTO {
    private Integer draw;
    private Long recordsTotal;
    private Long recordsFiltered;
    private List<?> data;
}
