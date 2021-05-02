package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.SysRoleDAO;
import com.leaf.eexamen.dao.SysUserDAO;
import com.leaf.eexamen.dao.SysUserSysRoleDAO;
import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.SysUserSysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.SysRoleEntity;
import com.leaf.eexamen.entity.SysUserEntity;
import com.leaf.eexamen.entity.SysUserSysRoleEntity;
import com.leaf.eexamen.entity.SysUserSysRoleEntityId;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.service.SysUserSysRoleService;
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
public class SysUserSysRoleServiceImpl implements SysUserSysRoleService {

    private SysUserDAO sysUserDAO;
    private SysRoleDAO sysRoleDAO;
    private SysUserSysRoleDAO sysUserSysRoleDAO;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DataTableResponseDTO getSysUserSysRoleForSysUser(SysUserDTO sysUserDTO) {
        Map<String, SysUserSysRoleDTO> sysUserSysRoleMap = new HashMap<>();
        List<SysUserSysRoleDTO> sysUserSysRoles = new ArrayList<>();
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        try {
            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserDTO.getUsername());

            sysRoleDAO.findAllSysRoleEntities(DefaultStatusEnum.ACTIVE.getCode()).stream()
                    .filter(sysRoleEntity -> !CommonConstant.SYSTEM.equals(sysRoleEntity.getCode()))
                    .forEach(sysRoleEntity -> {
                        SysUserSysRoleDTO sysUserSysRoleDTO = new SysUserSysRoleDTO();
                        sysUserSysRoleDTO.setUsername(sysUserEntity.getUsername());
                        sysUserSysRoleDTO.setName(sysUserEntity.getName());
                        sysUserSysRoleDTO.setSysRoleCode(sysRoleEntity.getCode());
                        sysUserSysRoleDTO.setSysRoleDescription(sysRoleEntity.getDescription());
                        sysUserSysRoleDTO.setEnable(false);

                        sysUserSysRoleMap.put(sysUserSysRoleDTO.getSysRoleCode(), sysUserSysRoleDTO);

                    });

            sysUserSysRoleDAO.getSysUserSysRoleEntitiesBySysUser(sysUserEntity.getUsername()).stream()
                    .forEach(sysUserSysRole -> {
                        SysUserSysRoleDTO sysUserSysRoleDTO = sysUserSysRoleMap.get(sysUserSysRole.getSysRoleEntity().getCode());
                        sysUserSysRoleDTO.setEnable(true);
                        sysUserSysRoleMap.put(sysUserSysRoleDTO.getSysRoleCode(), sysUserSysRoleDTO);
                    });

            sysUserSysRoles = new ArrayList<>(sysUserSysRoleMap.values());
            responseDTO.setData(sysUserSysRoles);

        } catch (Exception e) {
            log.info(e.getMessage());
            responseDTO.setData(sysUserSysRoles);
        }
        return responseDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForSysUserSysRole() {
        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {

            List<?> sysUser = sysUserDAO.findAllSysUsereEntities(DefaultStatusEnum.ACTIVE.getCode())
                    .stream()
                    .filter(sysUserEntity -> !Optional.ofNullable(sysUserEntity.getStudent()).orElse(false))
                    .filter(sysUserEntity -> !CommonConstant.SYSTEM.equals(sysUserEntity.getUsername()))
                    .map(su -> new DropDownDTO<>(su.getUsername(), su.getUsername())).collect(Collectors.toList());

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
    public ResponseDTO<SysUserSysRoleDTO> saveSysUserSysRole(SysUserSysRoleDTO sysUserSysRoleDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Sys User Sys Role Save Failed";
        try {
            SysUserSysRoleEntity sysUserSysRoleEntity = new SysUserSysRoleEntity();
            SysUserSysRoleEntityId id = new SysUserSysRoleEntityId();


            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserSysRoleDTO.getUsername());
            SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysUserSysRoleDTO.getSysRoleCode());


            id.setSysUser(sysUserEntity.getUsername());
            id.setSysRole(sysRoleEntity.getId());

            sysUserSysRoleEntity.setSysUserSysRoleEntityId(id);
            sysUserSysRoleEntity.setSysUserEntity(sysUserEntity);
            sysUserSysRoleEntity.setSysRoleEntity(sysRoleEntity);


            sysUserSysRoleDAO.saveSysUserSysRoleEntity(sysUserSysRoleEntity);

            description = "Sys User Sys Role Save Successfully";
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
    public ResponseDTO<SysUserSysRoleDTO> deleteSysUserSysRole(SysUserSysRoleDTO sysUserSysRoleDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Sys User Sys Role Delete Failed";
        try {

            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserSysRoleDTO.getUsername());
            SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysUserSysRoleDTO.getSysRoleCode());

            sysUserSysRoleDAO.deleteSysUserSysRoleEntityBySysUserAndSysRole(sysUserEntity.getUsername(), sysRoleEntity.getId());

            description = "Sys User Sys Role Delete Successfully";
            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }

}
