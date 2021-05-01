package com.leaf.eexamen.dto.common;

import lombok.Data;

import java.util.List;

/**
 * @author : rusiru on 7/6/19.
 */
@Data
public class MenuSectionDTO {

    private String description;
    private List<MenuDTO> menuDTOs;
}
