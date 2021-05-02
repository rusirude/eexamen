package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.SectionDAO;
import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dto.SectionDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.SectionEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.SectionService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SectionServiceImpl implements SectionService {

    private SectionDAO sectionDAO;
    private StatusDAO statusDAO;
    private StatusCategoryDAO statusCategoryDAO;

    private CommonMethod commonMethod;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<SectionDTO> saveSection(SectionDTO sectionDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Section Save Failed";

        SectionEntity sectionEntity;
        try {
            sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());
            if (sectionEntity == null) {
                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sectionDTO.getStatusCode());

                sectionEntity = new SectionEntity();
                sectionEntity.setCode(sectionDTO.getCode());
                sectionEntity.setDescription(sectionDTO.getDescription());
                sectionEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(sectionEntity);

                sectionDAO.saveSectionEntity(sectionEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Section Save Successfully";
            } else if (DeleteStatusEnum.DELETE.getCode().equals(sectionEntity.getStatusEntity().getCode())) {

                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sectionDTO.getStatusCode());

                sectionEntity.setCode(sectionDTO.getCode());
                sectionEntity.setDescription(sectionDTO.getDescription());
                sectionEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(sectionEntity);

                sectionDAO.updateSectionEntity(sectionEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Section Save Successfully";
            } else {
                description = "Section Code is Already Used ";
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
    public ResponseDTO<SectionDTO> updateSection(SectionDTO sectionDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Section Update Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(sectionDTO.getStatusCode());

            SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());
            sectionEntity.setCode(sectionDTO.getCode());
            sectionEntity.setDescription(sectionDTO.getDescription());
            sectionEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(sectionEntity);

            sectionDAO.updateSectionEntity(sectionEntity);
            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Section Update Successfully";
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
    public ResponseDTO<SectionDTO> deleteSection(SectionDTO sectionDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Section Delete Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

            SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());

            sectionEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(sectionEntity);

            sectionDAO.updateSectionEntity(sectionEntity);
            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Section Delete Successfully";
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
    public ResponseDTO<SectionDTO> findSection(SectionDTO sectionDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Section is Not Found";
        SectionDTO dto = new SectionDTO();
        try {

            SectionEntity sectionEntity = sectionDAO.findSectionEntityByCode(sectionDTO.getCode());

            if (sectionEntity != null
                    && !DeleteStatusEnum.DELETE.getCode().equals(sectionEntity.getStatusEntity().getCode())) {
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Section is Found Successfully";

                dto.setCode(sectionEntity.getCode());
                dto.setDescription(sectionEntity.getDescription());
                dto.setStatusCode(sectionEntity.getStatusEntity().getCode());
                dto.setStatusDescription(sectionEntity.getStatusEntity().getDescription());
                dto.setCreatedBy(sectionEntity.getCreatedBy());
                dto.setCreatedOn(sectionEntity.getCreatedOn());
                dto.setUpdatedBy(sectionEntity.getUpdatedBy());
                dto.setUpdatedOn(sectionEntity.getUpdatedOn());

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
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForSection() {

        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {
            List<?> status = statusCategoryDAO.findStatusCategoryByCode(StatusCategoryEnum.DEFAULT.getCode())
                    .getStatusEntities().stream()
                    .sorted(Comparator.comparing(StatusEntity::getDescription)).map(s -> new DropDownDTO<>(s.getCode(), s.getDescription()))
                    .collect(Collectors.toList());

            map.put("status", status);

            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, map);
    }

    @Override
    @Transactional
    public DataTableResponseDTO getSectionsForDataTable(DataTableRequestDTO dataTableRequestDTO) {
        List<SectionDTO> list;
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord;
        try {
            list = sectionDAO.<List<SectionEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
                    .stream().map(entity -> {
                        SectionDTO dto = new SectionDTO();
                        dto.setCode(entity.getCode());
                        dto.setDescription(entity.getDescription());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        dto.setCreatedBy(entity.getCreatedBy());
                        dto.setCreatedOn(entity.getCreatedOn());
                        dto.setUpdatedBy(entity.getUpdatedBy());
                        dto.setUpdatedOn(entity.getUpdatedOn());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = sectionDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

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
