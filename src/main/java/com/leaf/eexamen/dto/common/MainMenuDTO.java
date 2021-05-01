package com.leaf.eexamen.dto.common;

import java.util.List;

/**
 * @author : rusiru on 7/6/19.
 */
public class MainMenuDTO {

    private List<MenuSectionDTO> menuSectionDTOs;

    public MainMenuDTO(List<MenuSectionDTO> menuSectionDTOs) {
        this.menuSectionDTOs = menuSectionDTOs;
    }

    public List<MenuSectionDTO> getMenuSectionDTOs() {
        return menuSectionDTOs;
    }

    public void setMenuSectionDTOs(List<MenuSectionDTO> menuSectionDTOs) {
        this.menuSectionDTOs = menuSectionDTOs;
    }
}
