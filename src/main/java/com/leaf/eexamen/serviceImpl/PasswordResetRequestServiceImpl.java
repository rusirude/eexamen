package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.*;
import com.leaf.eexamen.dto.PasswordResetRequestDTO;
import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.*;
import com.leaf.eexamen.enums.*;
import com.leaf.eexamen.service.PasswordResetRequestService;
import com.leaf.eexamen.utility.CommonConstant;
import com.leaf.eexamen.utility.CommonMethod;
import com.leaf.eexamen.utility.MailSenderService;
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
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {

    private SysUserDAO sysUserDAO;
    private StatusDAO statusDAO;
    private MasterDataDAO masterDataDAO;
    private PasswordResetRequestDAO passwordResetRequestDAO;
    private EmailBodyDAO emailBodyDAO;
    private MailSenderService mailSenderService;

    private CommonMethod commonMethod;

    BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    @Transactional
    public ResponseDTO<?> createPasswordResetRequest(SysUserDTO sysUserDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Reset password is  Failed";
        SysUserEntity sysUserEntity;
        try {
            sysUserEntity = sysUserDAO.getSysUserEntityByUsername(sysUserDTO.getUsername());
            if ( Objects.isNull(sysUserEntity) || DeleteStatusEnum.DELETE.getCode().equalsIgnoreCase(sysUserEntity.getStatusEntity().getCode()))
                description = "User is Not Found";

            if (!Optional.ofNullable(passwordResetRequestDAO.findAllPasswordResetRequestsBySysUserAndStatus(sysUserEntity.getUsername(), PasswordResetRequestStatusEnum.REQUEST.getCode())).orElse(Collections.EMPTY_LIST).isEmpty())
                description = "Password Reset Request is pending";

            else {
                sysUserEntity.setResetRequest(true);
                commonMethod.getPopulateEntityWhenUpdate(sysUserEntity, sysUserDTO.getUsername());
                sysUserDAO.saveSysUserEntity(sysUserEntity);

                StatusEntity statusEntity = statusDAO.findStatusEntityByCode(PasswordResetRequestStatusEnum.REQUEST.getCode());

                PasswordResetRequestEntity passwordResetRequestEntity = new PasswordResetRequestEntity();
                passwordResetRequestEntity.setSysUserEntity(sysUserEntity);
                passwordResetRequestEntity.setStatusEntity(statusEntity);

                commonMethod.getPopulateEntityWhenInsert(passwordResetRequestEntity, sysUserDTO.getUsername());
                passwordResetRequestDAO.savePasswordResetRequestEntity(passwordResetRequestEntity);

                code = ResponseCodeEnum.SUCCESS.getCode();


                description = "Password Reset Request is Sent";
            }

        } catch (Exception e) {
           log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, description);
    }


    @Override
    @Transactional
    public ResponseDTO<?> resetPassword(Long requestId) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Reset password is  Failed";

        try {

            EmailBodyEntity emailBodyEntity = emailBodyDAO.findEmailBodyEntityByCode(EmailEnum.EFPR.getCode());
            PasswordResetRequestEntity passwordResetRequestEntity = passwordResetRequestDAO.findPasswordResetRequest(requestId);
            if (PasswordResetRequestStatusEnum.PRESET.getCode().equalsIgnoreCase(passwordResetRequestEntity.getStatusEntity().getCode()))
                description = "Password Reset Already";

            else {
                if (DeleteStatusEnum.DELETE.getCode().equalsIgnoreCase(passwordResetRequestEntity.getSysUserEntity().getStatusEntity().getCode()))
                    description = "User is Not Found";
                else {
                    SysUserEntity sysUserEntity = passwordResetRequestEntity.getSysUserEntity();

                    MasterDataEntity defaultPasswordMasterDataEntity = Optional.ofNullable(masterDataDAO.loadMasterDataEntity(MasterDataEnum.DEFAULT_PASSWORD.getCode())).orElse(new MasterDataEntity());
                    String password = ((Optional.ofNullable(sysUserEntity.getStudent()).orElse(false))?
                            (Optional.ofNullable(emailBodyEntity.getEnable()).orElse(false)?
                                    passwordGenerate()
                                    :Optional.ofNullable(defaultPasswordMasterDataEntity.getValue()).orElse(""))
                            :Optional.ofNullable(defaultPasswordMasterDataEntity.getValue()).orElse(""));
                    sysUserEntity.setResetRequest(false);
                    sysUserEntity.setPassword(bCryptPasswordEncoder.encode(password));
                    commonMethod.getPopulateEntityWhenUpdate(sysUserEntity);
                    sysUserDAO.saveSysUserEntity(sysUserEntity);

                    StatusEntity statusEntity = statusDAO.findStatusEntityByCode(PasswordResetRequestStatusEnum.PRESET.getCode());

                    passwordResetRequestEntity.setStatusEntity(statusEntity);

                    commonMethod.getPopulateEntityWhenUpdate(passwordResetRequestEntity);
                    passwordResetRequestDAO.updatePasswordResetRequestEntity(passwordResetRequestEntity);

                    if(Optional.ofNullable(sysUserEntity.getStudent()).orElse(false) && Optional.ofNullable(emailBodyEntity.getEnable()).orElse(false)){
                        String subject = emailBodyEntity.getSubject()
                                .replace("@Username",sysUserEntity.getUsername())
                                .replace("@Password",password);
                        String content = emailBodyEntity.getContent()
                                .replace("@Username",sysUserEntity.getUsername())
                                .replace("@Password",password);
                        mailSenderService.sendEmailWithPlainText(sysUserEntity.getUsername(), subject,content);
                    }

                    code = ResponseCodeEnum.SUCCESS.getCode();


                    description = "Password Reset is Rest";
                }
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
    public DataTableResponseDTO getPasswordResetRequestForDataTable(DataTableRequestDTO dataTableRequestDTO) {
        List<PasswordResetRequestDTO> list;
        DataTableResponseDTO responseDTO = new DataTableResponseDTO();
        Long numOfRecord;
        try {
            list = passwordResetRequestDAO.<List<PasswordResetRequestEntity>>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARCH_LIST)
                    .stream().map(entity -> {
                        PasswordResetRequestDTO dto = new PasswordResetRequestDTO();
                        dto.setId(entity.getId());
                        dto.setUsername(entity.getSysUserEntity().getUsername());
                        dto.setTitleDescription(entity.getSysUserEntity().getTitleEntity().getDescription());
                        dto.setName(entity.getSysUserEntity().getName());
                        dto.setStatusCode(entity.getStatusEntity().getCode());
                        dto.setStatusDescription(entity.getStatusEntity().getDescription());
                        dto.setCreatedBy(entity.getCreatedBy());
                        dto.setCreatedOn(entity.getCreatedOn());
                        dto.setUpdatedBy(entity.getUpdatedBy());
                        dto.setUpdatedOn(entity.getUpdatedOn());
                        return dto;
                    }).collect(Collectors.toList());

            numOfRecord = passwordResetRequestDAO.<Long>getDataForGrid(dataTableRequestDTO, CommonConstant.GRID_SEARC_COUNT);

            responseDTO.setData(list);
            responseDTO.setRecordsTotal(numOfRecord);
            responseDTO.setRecordsFiltered(numOfRecord);
            responseDTO.setDraw(dataTableRequestDTO.getDraw());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return responseDTO;
    }


    private String passwordGenerate(){
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[10];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< 10 ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password.toString();
    }

}
