package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.MasterDataEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.entity.SysUserEntity;
import com.leaf.eexamen.entity.TitleEntity;
import com.leaf.eexamen.enums.*;
import com.leaf.eexamen.service.SysUserService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl implements SysUserService {

    private SysUserDAO sysUserDAO;
    private StatusDAO statusDAO;
    private TitleDAO titleDAO;
    private StatusCategoryDAO statusCategoryDAO;
    private MasterDataDAO masterDataDAO;
    private SysUserAuthorityDAO sysUserAuthorityDAO;

    private CommonMethod commonMethod;

    BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<SysUserDTO> saveSysUser(SysUserDTO sysUserDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "User Save Failed";
        SysUserEntity sysUserEntity;
        try {
            sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserDTO.getUsername());
            MasterDataEntity defaultPasswordMasterDataEntity = Optional.ofNullable(masterDataDAO.loadMasterDataEntity(MasterDataEnum.DEFAULT_PASSWORD.getCode())).orElse(new MasterDataEntity());

            if (sysUserEntity == null) {
                sysUserEntity = new SysUserEntity();

                TitleEntity titleEntity = titleDAO.findTitleEntityByCode(sysUserDTO.getTitleCode());
                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sysUserDTO.getStatusCode());

                sysUserEntity.setUsername(sysUserDTO.getUsername());
                sysUserEntity.setPassword(bCryptPasswordEncoder.encode(Optional.ofNullable(defaultPasswordMasterDataEntity.getValue()).orElse("")));
                sysUserEntity.setTitleEntity(titleEntity);
                sysUserEntity.setName(sysUserDTO.getName());
                sysUserEntity.setStatusEntity(statusEntity);
                sysUserEntity.setResetRequest(false);
                sysUserEntity.setStudent(false);


                commonMethod.getPopulateEntityWhenInsert(sysUserEntity);

                sysUserDAO.saveSysUserEntity(sysUserEntity);

                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "User Save Successfully";

            } else {
                description = "Username is not Available in the System";
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
    public ResponseDTO<SysUserDTO> updateSysUser(SysUserDTO sysUserDTO) {

        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "User Update Failed";
        try {
            TitleEntity titleEntity = titleDAO.findTitleEntityByCode(sysUserDTO.getTitleCode());
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sysUserDTO.getStatusCode());

            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserDTO.getUsername());

            sysUserEntity.setTitleEntity(titleEntity);
            sysUserEntity.setName(sysUserDTO.getName());
            sysUserEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(sysUserEntity);

            sysUserDAO.updateSysUserEntity(sysUserEntity);

            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Use Update Successfully";

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
    public ResponseDTO<SysUserDTO> deleteSysUser(SysUserDTO sysUserDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "User Delete Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserDTO.getUsername());

            sysUserAuthorityDAO.deleteSysUserAuthorityEntityBySysUser(sysUserDTO.getUsername());

            sysUserEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(sysUserEntity);

            sysUserDAO.updateSysUserEntity(sysUserEntity);

            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "User Delete Successfully";
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
    public ResponseDTO<SysUserDTO> findSysUser(SysUserDTO sysUserDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "User is Not Found";
        SysUserDTO dto = new SysUserDTO();
        try {

            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserDTO.getUsername());

            if (sysUserEntity != null
                    && !DeleteStatusEnum.DELETE.getCode().equals(sysUserEntity.getStatusEntity().getCode())) {

                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "User is Found Successfully";

                dto.setUsername(sysUserEntity.getUsername());
                dto.setTitleCode(sysUserEntity.getTitleEntity().getCode());
                dto.setTitleDescription(sysUserEntity.getTitleEntity().getDescription());
                dto.setName(sysUserEntity.getName());
                dto.setStatusCode(sysUserEntity.getStatusEntity().getCode());
                dto.setStatusDescription(sysUserEntity.getStatusEntity().getDescription());
                dto.setCreatedBy(sysUserEntity.getCreatedBy());
                dto.setCreatedOn(sysUserEntity.getCreatedOn());
                dto.setUpdatedBy(sysUserEntity.getUpdatedBy());
                dto.setUpdatedOn(sysUserEntity.getUpdatedOn());

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
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForSysUser() {

        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {
            List<?> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
                    .getStatusEntities().stream()
                    .sorted(Comparator.comparing(StatusEntity::getDescription))
                    .map(s -> new DropDownDTO<>(s.getCode(), s.getDescription()))
                    .collect(Collectors.toList());

            List<?> title = titleDAO.findAllTitleEntities(DefaultStatusEnum.ACTIVE.getCode())
                    .stream().map(t -> new DropDownDTO<>(t.getCode(), t.getDescription()))
                    .collect(Collectors.toList());

            map.put("status", status);
            map.put("title", title);

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
    public DataTableResponseDTO getSysUsersForDataTable(DataTableRequestDTO dataTableRequestDTO) {
        List<SysUserDTO> list;
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord;
        try {
            list = sysUserDAO.<List<SysUserEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
                    .stream().map(entity -> {
                        SysUserDTO dto = new SysUserDTO();
                        dto.setUsername(entity.getUsername());
                        dto.setTitleDescription(entity.getTitleEntity().getDescription());
                        dto.setName(entity.getName());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        dto.setCreatedBy(entity.getCreatedBy());
                        dto.setCreatedOn(entity.getCreatedOn());
                        dto.setUpdatedBy(entity.getUpdatedBy());
                        dto.setUpdatedOn(entity.getUpdatedOn());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = sysUserDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

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
