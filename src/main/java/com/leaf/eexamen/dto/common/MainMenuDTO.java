package com.leaf.eexamen.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author : rusiru on 7/6/19.
 */
@Data
@AllArgsConstructor
public class MainMenuDTO {
    private List<MenuSectionDTO> menuSectionDTOs;
}
