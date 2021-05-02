package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.EmailBodyDAO;
import com.leaf.eexamen.dto.EmailBodyDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.EmailBodyEntity;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.service.EmailBodyService;
import com.leaf.eexamen.utility.CommonMethod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailBodyServiceImpl implements EmailBodyService {

    private EmailBodyDAO emailBodyDAO;
    private CommonMethod commonMethod;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ResponseDTO<EmailBodyDTO> updateEmailBody(EmailBodyDTO emailBodyDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Email Content Update is Failed";
        try {

            EmailBodyEntity emailEntity = emailBodyDAO.findEmailBodyEntityByCode(emailBodyDTO.getCode());
            emailEntity.setCode(emailBodyDTO.getCode());
            emailEntity.setSubject(emailBodyDTO.getSubject());
            emailEntity.setContent(emailBodyDTO.getContent());
            emailEntity.setEnable(Optional.ofNullable(emailBodyDTO.getEnable()).orElse(false));

            commonMethod.getPopulateEntityWhenUpdate(emailEntity);

            emailBodyDAO.updateEmailBodyEntity(emailEntity);
            code = ResponseCodeEnum.SUCCESS.getCode();
            description = "Email Content Update Successfully";
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
    public ResponseDTO<List<EmailBodyDTO>> findAllEmailBody() {
        String code = ResponseCodeEnum.FAILED.getCode();
        List<EmailBodyDTO> list = null;
        try {

            list = emailBodyDAO.findAllEmailBodyEntities()
                    .stream()
                    .map(emailBodyEntity -> {
                        EmailBodyDTO emailBodyDTO = new EmailBodyDTO();
                        emailBodyDTO.setCode(emailBodyEntity.getCode());
                        emailBodyDTO.setSubject(emailBodyEntity.getSubject());
                        emailBodyDTO.setContent(emailBodyEntity.getContent());
                        emailBodyDTO.setEnable(Optional.ofNullable(emailBodyEntity.getEnable()).orElse(false));

                        return emailBodyDTO;
                    }).collect(Collectors.toList());

            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseDTO<>(code, list);
    }
}
