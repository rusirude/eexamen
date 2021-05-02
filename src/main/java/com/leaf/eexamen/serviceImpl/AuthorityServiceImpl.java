package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.AuthorityDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.AuthorityEntity;
import com.leaf.eexamen.entity.SectionEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.AuthorityService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityDAO authorityDAO;
    private SectionDAO sectionDAO;
    private StatusDAO statusDAO;
    private StatusCategoryDAO statusCategoryDAO;
    private SysRoleAuthorityDAO sysRoleAuthorityDAO;
    private SysUserAuthorityDAO sysUserAuthorityDAO;

    private CommonMethod commonMethod;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<AuthorityDTO> saveAuthority(AuthorityDTO authorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Authority is saved Failed";

        AuthorityEntity authorityEntity;
        try {
            authorityEntity = authorityDAO.findAuthorityEntityByCode(authorityDTO.getCode());
            if (authorityEntity == null) {
                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(authorityDTO.getStatusCode());
                SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(authorityDTO.getSectionCode());

                authorityEntity = new AuthorityEntity();
                authorityEntity.setCode(authorityDTO.getCode());
                authorityEntity.setDescription(authorityDTO.getDescription());
                authorityEntity.setUrl(authorityDTO.getUrl());
                authorityEntity.setAuthCode(authorityDTO.getAuthCode());
                authorityEntity.setSectionEntity(sectionEntity);
                authorityEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(authorityEntity);

                authorityDAO.saveAuthorityEntity(authorityEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Authority is saved Successfully";
            } else if (DeleteStatusEnum.DELETE.getCode().equals(authorityEntity.getStatusEntity().getCode())) {

                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(authorityDTO.getStatusCode());
                SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(authorityDTO.getSectionCode());

                authorityEntity.setCode(authorityDTO.getCode());
                authorityEntity.setDescription(authorityDTO.getDescription());
                authorityEntity.setUrl(authorityDTO.getUrl());
                authorityEntity.setAuthCode(authorityDTO.getAuthCode());
                authorityEntity.setSectionEntity(sectionEntity);
                authorityEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(authorityEntity);

                authorityDAO.updateAuthorityEntity(authorityEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Authority is save Successfully";
            } else {
                description = "Authority Code is Already Used ";
            }

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
    public ResponseDTO<AuthorityDTO> updateAuthority(AuthorityDTO authorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Authority is updated Failed";
        try {

            AuthorityEntity authorityEntity = authorityDAO.findAuthorityEntityByCode(authorityDTO.getCode());

            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(authorityDTO.getStatusCode());
            SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(authorityDTO.getSectionCode());

            authorityEntity.setCode(authorityDTO.getCode());
            authorityEntity.setDescription(authorityDTO.getDescription());
            authorityEntity.setUrl(authorityDTO.getUrl());
            authorityEntity.setAuthCode(authorityDTO.getAuthCode());
            authorityEntity.setSectionEntity(sectionEntity);
            authorityEntity.setStatusEntity(statusEntity);


            commonMethod.getPopulateEntityWhenUpdate(authorityEntity);

            authorityDAO.updateAuthorityEntity(authorityEntity);

            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Authority is update Successfully";
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
    public ResponseDTO<AuthorityDTO> deleteAuthority(AuthorityDTO authorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Authority is delete Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

            AuthorityEntity authorityEntity = authorityDAO.findAuthorityEntityByCode(authorityDTO.getCode());

            sysRoleAuthorityDAO.deleteSysRoleAuthorityEntityByAuthority(authorityEntity.getId());
            sysUserAuthorityDAO.deleteSysUserAuthorityEntityByAuthority(authorityEntity.getId());

            authorityEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(authorityEntity);

            authorityDAO.updateAuthorityEntity(authorityEntity);

            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Authorty is delete Successfully";
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
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForAuthority() {
        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {
            List<?> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
                    .getStatusEntities()
                    .stream()
                    .map(s -> new DropDownDTO<>(s.getCode(), s.getDescription()))
                    .collect(Collectors.toList());
            List<?> sections = sectionDAO.findAllSectionEntities(DefaultStatusEnum.ACTIVE.getCode())
                    .stream().map(sc -> new DropDownDTO<>(sc.getCode(), sc.getDescription())).collect(Collectors.toList());

            map.put("status", status);
            map.put("sections", sections);

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
    public ResponseDTO<AuthorityDTO> findAuthority(AuthorityDTO authorityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Authority is not found";
        AuthorityDTO dto = new AuthorityDTO();
        try {

            AuthorityEntity authorityEntity = authorityDAO.findAuthorityEntityByCode(authorityDTO.getCode());

            if (authorityEntity != null
                    && !DeleteStatusEnum.DELETE.getCode().equals(authorityEntity.getStatusEntity().getCode())) {
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Authority is Found Successfully";

                dto.setCode(authorityEntity.getCode());
                dto.setDescription(authorityEntity.getDescription());
                dto.setUrl(authorityEntity.getUrl());
                dto.setAuthCode(authorityEntity.getAuthCode());
                dto.setSectionCode(authorityEntity.getSectionEntity().getCode());
                dto.setSectionDescription(authorityEntity.getSectionEntity().getDescription());
                dto.setStatusCode(authorityEntity.getStatusEntity().getCode());
                dto.setStatusDescription(authorityEntity.getStatusEntity().getDescription());
                dto.setCreatedBy(authorityEntity.getCreatedBy());
                dto.setCreatedOn(authorityEntity.getCreatedOn());
                dto.setUpdatedBy(authorityEntity.getUpdatedBy());
                dto.setUpdatedOn(authorityEntity.getUpdatedOn());

            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description, dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DataTableResponseDTO getAuthoritiesForDataTable(DataTableRequestDTO dataTableRequestDTO) {
        List<AuthorityDTO> list;
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord;
        try {
            list = authorityDAO.<List<AuthorityEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
                    .stream().map(entity -> {
                        AuthorityDTO dto = new AuthorityDTO();
                        dto.setCode(entity.getCode());
                        dto.setDescription(entity.getDescription());
                        dto.setUrl(entity.getUrl());
                        dto.setAuthCode(entity.getAuthCode());
                        dto.setSectionDescription(entity.getSectionEntity().getDescription());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        dto.setCreatedBy(entity.getCreatedBy());
                        dto.setCreatedOn(entity.getCreatedOn());
                        dto.setUpdatedBy(entity.getUpdatedBy());
                        dto.setUpdatedOn(entity.getUpdatedOn());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = authorityDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

            responseDTO.setData(list);
            responseDTO.setRecordsTotal(numOfRecord);
            responseDTO.setRecordsFiltered(numOfRecord);
            responseDTO.setDraw(dataTableRequestDTO.getDraw());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseDTO;
    }

}
