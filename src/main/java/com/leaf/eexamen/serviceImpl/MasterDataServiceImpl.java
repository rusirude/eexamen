package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.MasterDataDAO;
import com.leaf.eexamen.dao.SysRoleDAO;
import com.leaf.eexamen.dto.MasterDataDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.MasterDataEntity;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.service.MasterDataService;
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
public class MasterDataServiceImpl implements MasterDataService {

    private MasterDataDAO masterDataDAO;
    private SysRoleDAO sysRoleDAO;
    private CommonMethod commonMethod;



    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForMasterData() {

        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {
            List<?> sysRole = sysRoleDAO.findAllSysRoleEntities(DefaultStatusEnum.ACTIVE.getCode())
                    .stream()
                    .filter(sysRoleEntity -> !CommonConstant.SYSTEM.equals(sysRoleEntity.getCode()))
                    .map(c -> new DropDownDTO<>(c.getCode(), c.getDescription()))
                    .collect(Collectors.toList());

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
    public ResponseDTO<List<MasterDataDTO>> loadAllMasterData() {
        String code = ResponseCodeEnum.FAILED.getCode();
        List<MasterDataDTO> masterData = null;
        try {
            masterData = masterDataDAO.findAllMasterDataEntities()
                    .stream().map(entity -> {
                        MasterDataDTO dataDTO = new MasterDataDTO();
                        dataDTO.setCode(entity.getCode());
                        dataDTO.setValue(entity.getValue());
                        return dataDTO;
                    }).collect(Collectors.toList());
            code = ResponseCodeEnum.SUCCESS.getCode();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, masterData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<MasterDataDTO> saveOrUpdateMasterData(List<MasterDataDTO> masterDataDTOS) {

        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Master Data Save Faild";
        try {
            masterDataDTOS.forEach(dto -> {
                MasterDataEntity entity = masterDataDAO.findMasterDataEntity(dto.getCode());
                if (entity != null) {
                    entity.setValue(String.valueOf(dto.getValue()));
                    commonMethod.getPopulateEntityWhenUpdate(entity);

                    masterDataDAO.updateMasterDataEntity(entity);
                } else {
                    entity = new MasterDataEntity();
                    entity.setCode(dto.getCode());
                    entity.setValue(String.valueOf(dto.getValue()));
                    commonMethod.getPopulateEntityWhenInsert(entity);

                    masterDataDAO.saveMasterDataEntity(entity);
                }
            });

            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Master Data Saved Successfully";

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }

}
