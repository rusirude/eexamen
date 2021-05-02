package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.AuthorityDAO;
import com.leaf.eexamen.dao.SysRoleAuthorityDAO;
import com.leaf.eexamen.dao.SysRoleDAO;
import com.leaf.eexamen.dto.SysRoleAuthorityDTO;
import com.leaf.eexamen.dto.SysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.AuthorityEntity;
import com.leaf.eexamen.entity.SysRoleAuthorityEntity;
import com.leaf.eexamen.entity.SysRoleAuthorityEntityId;
import com.leaf.eexamen.entity.SysRoleEntity;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.service.SysRoleAuthorityService;
import com.leaf.eexamen.utility.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SysRoleAuthorityServiceImpl implements SysRoleAuthorityService {


    private SysRoleDAO sysRoleDAO;
    private SysRoleAuthorityDAO sysRoleAuthorityDAO;
    private AuthorityDAO authorityDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DataTableResponseDTO getSysRoleAuthorityForSysRole(SysRoleDTO sysRoleDTO) {
        Map<String, SysRoleAuthorityDTO> sysRoleAuthorityMap = new HashMap<>();
        List<SysRoleAuthorityDTO> sysRoleAuthorities = new ArrayList<>();
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        try {
            SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysRoleDTO.getCode());

            authorityDAO.findAuthorityEntitiesByStatus(DefaultStatusEnum.ACTIVE.getCode()).stream()
                    .forEach(authority -> {
                        SysRoleAuthorityDTO sysRoleAuthorityDTO = new SysRoleAuthorityDTO();
                        sysRoleAuthorityDTO.setSysRoleCode(sysRoleEntity.getCode());
                        sysRoleAuthorityDTO.setSysRoleDescription(sysRoleEntity.getDescription());
                        sysRoleAuthorityDTO.setAuthorityCode(authority.getCode());
                        sysRoleAuthorityDTO.setAuthorityDescription(authority.getDescription());
                        sysRoleAuthorityDTO.setEnable(false);

                        sysRoleAuthorityMap.put(sysRoleAuthorityDTO.getAuthorityCode(), sysRoleAuthorityDTO);

                    });

            sysRoleAuthorityDAO.getSysRoleAuthorityEntitiesBySysRole(sysRoleEntity.getId()).stream()
                    .forEach(sysRoleAuthority -> {
                        SysRoleAuthorityDTO sysRoleAuthorityDTO = sysRoleAuthorityMap
                                .get(sysRoleAuthority.getAuthorityEntity().getCode());
                        sysRoleAuthorityDTO.setEnable(true);
                        sysRoleAuthorityMap.put(sysRoleAuthorityDTO.getAuthorityCode(), sysRoleAuthorityDTO);
                    });

            sysRoleAuthorities = sysRoleAuthorityMap.values().stream().collect(Collectors.toList());
            responseDTO.setData(sysRoleAuthorities);

        } catch (Exception e) {
            log.info(e.getMessage());
            responseDTO.setData(sysRoleAuthorities);
        }
        return responseDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForSysRoleAuthority() {

        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {

            List<?> sysRole = sysRoleDAO.findAllSysRoleEntities(DefaultStatusEnum.ACTIVE.getCode())
                    .stream()
                    .filter(sysRoleEntity -> !CommonConstant.SYSTEM.equals(sysRoleEntity.getCode()))
                    .map(ra -> new DropDownDTO<>(ra.getCode(), ra.getDescription())).collect(Collectors.toList());

            map.put("sysRole", sysRole);

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
    public ResponseDTO<SysRoleAuthorityDTO> saveSysRoleAuthority(SysRoleAuthorityDTO sysRoleAuthorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Sys Role Authority Save Failed";
        try {
            SysRoleAuthorityEntity sysRoleAuthorityEntity = new SysRoleAuthorityEntity();
            SysRoleAuthorityEntityId id = new SysRoleAuthorityEntityId();


            SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysRoleAuthorityDTO.getSysRoleCode());
            AuthorityEntity authorityEntity = authorityDAO.findAuthorityEntityByCode(sysRoleAuthorityDTO.getAuthorityCode());

            id.setSysRole(sysRoleEntity.getId());
            id.setAuthority(authorityEntity.getId());

            sysRoleAuthorityEntity.setSysRoleAuthorityEntityId(id);
            sysRoleAuthorityEntity.setSysRoleEntity(sysRoleEntity);
            sysRoleAuthorityEntity.setAuthorityEntity(authorityEntity);

            sysRoleAuthorityDAO.saveSysRoleAuthorityentity(sysRoleAuthorityEntity);

            description = "Sys Role Authority Save Successfully";
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
    public ResponseDTO<SysRoleAuthorityDTO> deleteSysRoleAuthority(SysRoleAuthorityDTO sysRoleAuthorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Sys Role Authority Delete Failed";
        try {

            SysRoleEntity sysRoleEntity = sysRoleDAO.findSysRoleEntityByCode(sysRoleAuthorityDTO.getSysRoleCode());
            AuthorityEntity authorityEntity = authorityDAO.findAuthorityEntityByCode(sysRoleAuthorityDTO.getAuthorityCode());

            sysRoleAuthorityDAO.deleteSysRoleAuthorityEntityBySysRoleAndAuthority(sysRoleEntity.getId(), authorityEntity.getId());

            description = "Sys Role Authority Delete Successfully";
            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }


}
