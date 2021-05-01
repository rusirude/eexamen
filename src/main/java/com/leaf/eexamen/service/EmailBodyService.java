package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.EmailBodyDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.List;

public interface EmailBodyService {
    /**
     * Update EmailBody
     *
     * @param emailBodyDTO
     * @return {@link ResponseDTO}
     */
    ResponseDTO<EmailBodyDTO> updateEmailBody(EmailBodyDTO emailBodyDTO);

    /**
     * Find All EmailBody
     */
    ResponseDTO<List<EmailBodyDTO>> findAllEmailBody();
}
