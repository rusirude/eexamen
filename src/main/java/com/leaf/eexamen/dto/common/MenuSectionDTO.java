package com.leaf.eexamen.dto.common;

import java.util.List;

/**
 * @author : rusiru on 7/6/19.
 */
public class MenuSectionDTO {

    private String description;
    private List<MenuDTO> menuDTOs;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuDTO> getMenuDTOs() {
        return menuDTOs;
    }

    public void setMenuDTOs(List<MenuDTO> menuDTOs) {
        this.menuDTOs = menuDTOs;
    }
}
