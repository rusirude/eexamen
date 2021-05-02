package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.CityDAO;
import com.leaf.eexamen.dao.StatusCategoryDAO;
import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dto.CityDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.DropDownDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.CityEntity;
import com.leaf.eexamen.entity.StatusEntity;
import com.leaf.eexamen.enums.DeleteStatusEnum;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.enums.StatusCategoryEnum;
import com.leaf.eexamen.service.CityService;
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
public class CityServiceImpl implements CityService {

    private CityDAO cityDAO;
    private StatusDAO statusDAO;
    private StatusCategoryDAO statusCategoryDAO;

    private CommonMethod commonMethod;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<CityDTO> saveCity(CityDTO cityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "City is save Failed";

        CityEntity cityEntity;
        try {
            cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());
            if (cityEntity == null) {
                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(cityDTO.getStatusCode());

                cityEntity = new CityEntity();
                cityEntity.setCode(cityDTO.getCode());
                cityEntity.setDescription(cityDTO.getDescription());
                cityEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(cityEntity);

                cityDAO.saveCityEntity(cityEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "City is saved Successfully";
            } else if (DeleteStatusEnum.DELETE.getCode().equals(cityEntity.getStatusEntity().getCode())) {

                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(cityDTO.getStatusCode());

                cityEntity.setCode(cityDTO.getCode());
                cityEntity.setDescription(cityDTO.getDescription());
                cityEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(cityEntity);

                cityDAO.updateCityEntity(cityEntity);
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "City is save Successfully";
            } else {
                description = "City Code is Already Used ";
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
    public ResponseDTO<CityDTO> updateCity(CityDTO cityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "City is updated Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(cityDTO.getStatusCode());

            CityEntity cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());
            cityEntity.setCode(cityDTO.getCode());
            cityEntity.setDescription(cityDTO.getDescription());
            cityEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(cityEntity);

            cityDAO.updateCityEntity(cityEntity);
            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "City is updated Successfully";
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
    public ResponseDTO<CityDTO> deleteCity(CityDTO cityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "City is delete Failed";
        try {
            StatusEntity statusEntity = statusDAO.findStatusEntityByCode(DeleteStatusEnum.DELETE.getCode());

            CityEntity cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());

            cityEntity.setStatusEntity(statusEntity);

            commonMethod.getPopulateEntityWhenUpdate(cityEntity);

            cityDAO.updateCityEntity(cityEntity);
            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "City delete Successfully";
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
    public ResponseDTO<CityDTO> findCity(CityDTO cityDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "City is Not Found";
        CityDTO dto = new CityDTO();
        try {

            CityEntity cityEntity = cityDAO.findCityEntityByCode(cityDTO.getCode());

            if (cityEntity != null
                    && !DeleteStatusEnum.DELETE.getCode().equals(cityEntity.getStatusEntity().getCode())) {
                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "City is Found Successfully";

                dto.setCode(cityEntity.getCode());
                dto.setDescription(cityEntity.getDescription());
                dto.setStatusCode(cityEntity.getStatusEntity().getCode());
                dto.setStatusDescription(cityEntity.getStatusEntity().getDescription());
                dto.setCreatedBy(cityEntity.getCreatedBy());
                dto.setCreatedOn(cityEntity.getCreatedOn());
                dto.setUpdatedBy(cityEntity.getUpdatedBy());
                dto.setUpdatedOn(cityEntity.getUpdatedOn());

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
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForCity() {

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
    public DataTableResponseDTO getCitysForDataTable(DataTableRequestDTO dataTableRequestDTO) {
        List<CityDTO> list;
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord;
        try {
            list = cityDAO.<List<CityEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
                    .stream().map(entity -> {
                        CityDTO dto = new CityDTO();
                        dto.setCode(entity.getCode());
                        dto.setDescription(entity.getDescription());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        dto.setCreatedBy(entity.getCreatedBy());
                        dto.setCreatedOn(entity.getCreatedOn());
                        dto.setUpdatedBy(entity.getUpdatedBy());
                        dto.setUpdatedOn(entity.getUpdatedOn());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = cityDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

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
