package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dao.TitleDAO;
import com.leaf.eexamen.dto.TitleDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.entity.TitleEntity;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.TitleService;
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
public class TitleServiceImpl implements TitleService {

    private TitleDAO titleDAO;
    private StatusDAO statusDAO;
    private StatusCategoryDAO statusCategoryDAO;

    private CommonMethod commonMethod;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<TitleDTO> saveTitle(TitleDTO titleDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Title Save Failed";

        TitleEntity titleEntity;
        try {
            titleEntity = titleDAO.findTitleEntityByCode(titleDTO.getCode());
            if (titleEntity == null) {
                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(titleDTO.getStatusCode());

                titleEntity = new TitleEntity();
                titleEntity.setCode(titleDTO.getCode());
                titleEntity.setDescription(titleDTO.getDescription());
                titleEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(titleEntity);

                titleDAO.saveTitleEntity(titleEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Title Save Successfully";
            } else if (DeleteStatusEnum.DELETE.getCode().equals(titleEntity.getStatusEntity().getCode())) {

                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(titleDTO.getStatusCode());

                titleEntity.setCode(titleDTO.getCode());
                titleEntity.setDescription(titleDTO.getDescription());
                titleEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(titleEntity);

                titleDAO.updateTitleEntity(titleEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Title Save Successfully";
            } else {
                description = "Title Code is Already Used ";
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
    public ResponseDTO<TitleDTO> updateTitle(TitleDTO titleDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Title Update Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(titleDTO.getStatusCode());

            TitleEntity titleEntity = titleDAO.findTitleEntityByCode(titleDTO.getCode());
            titleEntity.setCode(titleDTO.getCode());
            titleEntity.setDescription(titleDTO.getDescription());
            titleEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(titleEntity);

            titleDAO.updateTitleEntity(titleEntity);
            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Title Update Successfully";
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
    public ResponseDTO<TitleDTO> deleteTitle(TitleDTO titleDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Title Delete Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

            TitleEntity titleEntity = titleDAO.findTitleEntityByCode(titleDTO.getCode());

            titleEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(titleEntity);

            titleDAO.updateTitleEntity(titleEntity);
            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Title Delete Successfully";
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
    public ResponseDTO<TitleDTO> findTitle(TitleDTO titleDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Title is Not Found";
        TitleDTO dto = new TitleDTO();
        try {

            TitleEntity titleEntity = titleDAO.findTitleEntityByCode(titleDTO.getCode());

            if (titleEntity != null
                    && !DeleteStatusEnum.DELETE.getCode().equals(titleEntity.getStatusEntity().getCode())) {
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Title is Found Successfully";

                dto.setCode(titleEntity.getCode());
                dto.setDescription(titleEntity.getDescription());
                dto.setStatusCode(titleEntity.getStatusEntity().getCode());
                dto.setStatusDescription(titleEntity.getStatusEntity().getDescription());
                dto.setCreatedBy(titleEntity.getCreatedBy());
                dto.setCreatedOn(titleEntity.getCreatedOn());
                dto.setUpdatedBy(titleEntity.getUpdatedBy());
                dto.setUpdatedOn(titleEntity.getUpdatedOn());

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
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForTitle() {

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
    public DataTableResponseDTO getTitlesForDataTable(DataTableRequestDTO dataTableRequestDTO) {
        List<TitleDTO> list;
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord;
        try {
            list = titleDAO.<List<TitleEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
                    .stream().map(entity -> {
                        TitleDTO dto = new TitleDTO();
                        dto.setCode(entity.getCode());
                        dto.setDescription(entity.getDescription());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        dto.setCreatedBy(entity.getCreatedBy());
                        dto.setCreatedOn(entity.getCreatedOn());
                        dto.setUpdatedBy(entity.getUpdatedBy());
                        dto.setUpdatedOn(entity.getUpdatedOn());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = titleDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

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
