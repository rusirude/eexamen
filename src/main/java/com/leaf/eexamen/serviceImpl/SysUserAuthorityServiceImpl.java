package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.SysUserAuthorityDTO;
import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.*;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.service.SysUserAuthorityService;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserAuthorityServiceImpl implements SysUserAuthorityService {


    private SysUserDAO sysUserDAO;
    private SysRoleAuthorityDAO sysRoleAuthorityDAO;
    private SysUserAuthorityDAO sysUserAuthorityDAO;
    private SysUserSysRoleDAO sysUserSysRoleDAO;
    private AuthorityDAO authorityDAO;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DataTableResponseDTO getSysUserAuthorityForSysUser(SysUserDTO sysUserDTO) {
        Map<String, SysUserAuthorityDTO> sysUserAuthorityMap = new HashMap<>();
        List<SysUserAuthorityDTO> sysUserAuthorities = new ArrayList<>();
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        try {
            List<SysRoleEntity> sysRoleEntities = sysUserSysRoleDAO.getSysUserSysRoleEntitiesBySysUser(sysUserDTO.getUsername())
                    .stream()
                    .map(entity -> entity.getSysRoleEntity())
                    .collect(Collectors.toList());
            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserDTO.getUsername());

            authorityDAO.findAuthorityEntitiesByStatus(DefaultStatusEnum.ACTIVE.getCode()).stream()
                    .forEach(authority -> {
                        SysUserAuthorityDTO sysUserAuthorityDTO = new SysUserAuthorityDTO();
                        sysUserAuthorityDTO.setUsername(sysUserDTO.getUsername());
                        sysUserAuthorityDTO.setTitleDescripton(sysUserEntity.getTitleEntity().getDescription());
                        sysUserAuthorityDTO.setName(sysUserEntity.getName());
                        sysUserAuthorityDTO.setAuthorityCode(authority.getCode());
                        sysUserAuthorityDTO.setAuthorityDescription(authority.getDescription());
                        sysUserAuthorityDTO.setEnable(false);

                        sysUserAuthorityMap.put(sysUserAuthorityDTO.getAuthorityCode(), sysUserAuthorityDTO);

                    });

            if (!sysRoleEntities.isEmpty()) {
                sysRoleAuthorityDAO.getSysRoleAuthorityEntitiesBySysRoles(sysRoleEntities).stream()
                        .forEach(sysRoleAuthority -> {
                            SysUserAuthorityDTO sysUserAuthorityDTO = Optional.ofNullable(sysUserAuthorityMap
                                    .get(sysRoleAuthority.getAuthorityEntity().getCode())).orElse(null);
                            if (sysUserAuthorityDTO != null) {
                                sysUserAuthorityDTO.setEnable(true);
                                sysUserAuthorityMap.put(sysUserAuthorityDTO.getAuthorityCode(), sysUserAuthorityDTO);
                            }

                        });
            }

            sysUserAuthorityDAO.getSysUserAuthorityEntitiesBySysUser(sysUserDTO.getUsername()).stream()
                    .forEach(sysUserAuthorty -> {
                        SysUserAuthorityDTO sysUserAuthorityDTO = Optional.ofNullable(sysUserAuthorityMap
                                .get(sysUserAuthorty.getAuthorityEntity().getCode())).orElse(null);
                        if (sysUserAuthorityDTO != null) {
                            sysUserAuthorityDTO.setEnable(sysUserAuthorty.getIsGrant() == 1);
                            sysUserAuthorityMap.put(sysUserAuthorityDTO.getAuthorityCode(), sysUserAuthorityDTO);
                        }
                    });


            sysUserAuthorities = sysUserAuthorityMap.values().stream().collect(Collectors.toList());
            responseDTO.setData(sysUserAuthorities);

        } catch (Exception e) {
            log.info(e.getMessage());
            responseDTO.setData(sysUserAuthorities);
        }
        return responseDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForSysUserAuthority() {

        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {

            List<?> sysUser = sysUserDAO.findAllSysUsereEntities(DefaultStatusEnum.ACTIVE.getCode())
                    .stream()
                    .filter(sysUserEntity -> !Optional.ofNullable(sysUserEntity.getStudent()).orElse(false))
                    .filter(sysUserEntity -> !CommonConstant.SYSTEM.equals(sysUserEntity.getUsername()))
                    .map(ra -> new DropDownDTO<>(ra.getUsername(), ra.getUsername())).collect(Collectors.toList());


            map.put("sysUser", sysUser);

            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<SysUserAuthorityDTO> saveSysUserAuthority(SysUserAuthorityDTO sysUserAuthorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Sys User Authority Save Faield";
        try {
            boolean[] isSysRoleAuthority = {false};
            List<SysRoleEntity> sysRoleEntities = sysUserSysRoleDAO.getSysUserSysRoleEntitiesBySysUser(sysUserAuthorityDTO.getUsername())
                    .stream()
                    .map(entity -> entity.getSysRoleEntity())
                    .collect(Collectors.toList());
            if (!sysRoleEntities.isEmpty()) {
                sysRoleAuthorityDAO.getSysRoleAuthorityEntitiesBySysRoles(sysRoleEntities)
                        .forEach(sysRoleAuthority -> {
                            if (sysUserAuthorityDTO.getAuthorityCode().equals(sysRoleAuthority.getAuthorityEntity().getCode())) {
                                isSysRoleAuthority[0] = true;
                            }
                        });
            }

            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserAuthorityDTO.getUsername());
            AuthorityEntity authorityEntity = authorityDAO.findAuthorityEntityByCode(sysUserAuthorityDTO.getAuthorityCode());


            if (isSysRoleAuthority[0]) {
                sysUserAuthorityDAO.deleteSysUserAuthorityEntityBySysUserAndAuthority(sysUserAuthorityDTO.getUsername(), authorityEntity.getId());
            } else {

                sysUserAuthorityDAO.deleteSysUserAuthorityEntityBySysUserAndAuthority(sysUserAuthorityDTO.getUsername(), authorityEntity.getId());

                SysUserAuthorityEntity sysUserAuthorityEntity = new SysUserAuthorityEntity();
                SysUserAuthorityEntityId id = new SysUserAuthorityEntityId();

                id.setSysUser(sysUserAuthorityDTO.getUsername());
                id.setAuthority(authorityEntity.getId());

                sysUserAuthorityEntity.setSysUserAuthorityEntityId(id);
                sysUserAuthorityEntity.setSysUserEntity(sysUserEntity);
                sysUserAuthorityEntity.setAuthorityEntity(authorityEntity);
                sysUserAuthorityEntity.setIsGrant(1L);

                sysUserAuthorityDAO.saveSysUserAuthorityEntity(sysUserAuthorityEntity);

            }

            description = "Sys User Authority Save Successfully";
            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<SysUserAuthorityDTO> deleteSysUserAuthority(SysUserAuthorityDTO sysUserAuthorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Sys User Authority Delete Faield";
        try {

            boolean[] isSysRoleAuthority = {false};
            List<SysRoleEntity> sysRoleEntities = sysUserSysRoleDAO.getSysUserSysRoleEntitiesBySysUser(sysUserAuthorityDTO.getUsername())
                    .stream()
                    .map(entity -> entity.getSysRoleEntity())
                    .collect(Collectors.toList());
            if (!sysRoleEntities.isEmpty()) {
                sysRoleAuthorityDAO.getSysRoleAuthorityEntitiesBySysRoles(sysRoleEntities).stream()
                        .forEach(sysRoleAuthority -> {
                            if (sysUserAuthorityDTO.getAuthorityCode().equals(sysRoleAuthority.getAuthorityEntity().getCode())) {
                                isSysRoleAuthority[0] = true;
                            }
                        });
            }

            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserAuthorityDTO.getUsername());
            AuthorityEntity authorityEntity = authorityDAO.findAuthorityEntityByCode(sysUserAuthorityDTO.getAuthorityCode());


            if (isSysRoleAuthority[0]) {

                sysUserAuthorityDAO.deleteSysUserAuthorityEntityBySysUserAndAuthority(sysUserAuthorityDTO.getUsername(), authorityEntity.getId());

                SysUserAuthorityEntity sysUserAuthorityEntity = new SysUserAuthorityEntity();
                SysUserAuthorityEntityId id = new SysUserAuthorityEntityId();

                id.setSysUser(sysUserAuthorityDTO.getUsername());
                id.setAuthority(authorityEntity.getId());

                sysUserAuthorityEntity.setSysUserAuthorityEntityId(id);
                sysUserAuthorityEntity.setSysUserEntity(sysUserEntity);
                sysUserAuthorityEntity.setAuthorityEntity(authorityEntity);
                sysUserAuthorityEntity.setIsGrant(0L);

                sysUserAuthorityDAO.saveSysUserAuthorityEntity(sysUserAuthorityEntity);

            } else {
                sysUserAuthorityDAO.deleteSysUserAuthorityEntityBySysUserAndAuthority(sysUserAuthorityDTO.getUsername(), authorityEntity.getId());
            }

            description = "Sys User Authority Delete Successfully";
            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }


}
