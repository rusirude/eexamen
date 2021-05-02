package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.SysRoleAuthorityDAO;
import com.leaf.eexamen.dao.SysUserAuthorityDAO;
import com.leaf.eexamen.dao.SysUserDAO;
import com.leaf.eexamen.dto.common.MainMenuDTO;
import com.leaf.eexamen.dto.common.MenuDTO;
import com.leaf.eexamen.dto.common.MenuSectionDTO;
import com.leaf.eexamen.entity.AuthorityEntity;
import com.leaf.eexamen.entity.SysRoleEntity;
import com.leaf.eexamen.entity.SysUserEntity;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.service.DashboardService;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : rusiru on 7/6/19.
 */

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DashboardServiceImpl implements DashboardService {

    private CommonMethod commonMethod;
    private SysRoleAuthorityDAO sysRoleAuthorityDAO;
    private SysUserAuthorityDAO sysUserAuthorityDAO;
    private SysUserDAO sysUserDAO;


    public MainMenuDTO loadMainMenu() {

        Map<String, Set<AuthorityEntity>> menuMap = new HashMap<>();

        SysUserEntity user = sysUserDAO.getSysUserEntityByUsername(commonMethod.getUsername());

        List<SysRoleEntity> sysRoles = new ArrayList<>();
        user.getSysUserSysRoleEntities()
                .forEach(sysUserSysRole -> {
                    sysRoles.add(sysUserSysRole.getSysRoleEntity());
                });
        if (!sysRoles.isEmpty()) {
            sysRoleAuthorityDAO.getSysRoleAuthorityEntitiesBySysRolesAndAnuthorityStatusAndSysRoleStatus(sysRoles, DefaultStatusEnum.ACTIVE.getCode(), DefaultStatusEnum.ACTIVE.getCode())
                    .stream()
                    .filter(sysRoleAuthorityEntity -> DefaultStatusEnum.ACTIVE.getCode().equalsIgnoreCase(sysRoleAuthorityEntity.getAuthorityEntity().getSectionEntity().getStatusEntity().getCode()))
                    .forEach(roleAuthority -> {
                        String key = roleAuthority.getAuthorityEntity().getSectionEntity().getCode() + "-" + roleAuthority.getAuthorityEntity().getSectionEntity().getDescription();
                        if (!menuMap.containsKey(key)) {
                            Set<AuthorityEntity> set = new HashSet<>();
                            set.add(roleAuthority.getAuthorityEntity());
                            menuMap.put(key, set);
                        } else {
                            menuMap.get(key).add(roleAuthority.getAuthorityEntity());
                        }
                    });
        }

        sysUserAuthorityDAO.getSysUserAuthorityEntitiesBySysUser(user.getUsername())
                .stream()
                .filter(userAuthority -> DefaultStatusEnum.ACTIVE.getCode().equalsIgnoreCase(userAuthority.getAuthorityEntity().getSectionEntity().getStatusEntity().getCode()))
                .forEach(userAuthority -> {
                    String key = userAuthority.getAuthorityEntity().getSectionEntity().getCode() + "-" + userAuthority.getAuthorityEntity().getSectionEntity().getDescription();

                    long isEnabled = userAuthority.getIsGrant();
                    if (isEnabled == 1) {
                        if (!menuMap.containsKey(key)) {
                            Set<AuthorityEntity> set = new HashSet<>();
                            set.add(userAuthority.getAuthorityEntity());
                            menuMap.put(key, set);
                        } else {
                            menuMap.get(key).add(userAuthority.getAuthorityEntity());
                        }
                    } else {
                        if (menuMap.containsKey(key)) {
                            menuMap.get(key).remove(userAuthority.getAuthorityEntity());
                        }
                    }

                });


        List<MenuSectionDTO> sectionDTOs = menuMap.entrySet()
                .stream()
                .map(stringSetEntry -> {
                    MenuSectionDTO section = new MenuSectionDTO();
                    section.setDescription(stringSetEntry.getKey().split("-")[1]);
                    List<MenuDTO> menu = stringSetEntry.getValue()
                            .stream()
                            .sorted(Comparator.comparing(AuthorityEntity::getDescription))
                            .map(authorityEntity -> {
                                MenuDTO menuItem = new MenuDTO();
                                menuItem.setDescription(authorityEntity.getDescription());
                                menuItem.setUrl(authorityEntity.getUrl());
                                return menuItem;
                            }).collect(Collectors.toList());
                    section.setMenuDTOs(menu);
                    return section;
                }).collect(Collectors.toList());

        return new MainMenuDTO(sectionDTOs);
    }
}
