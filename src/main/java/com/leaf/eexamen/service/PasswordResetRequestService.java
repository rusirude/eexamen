package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

public interface PasswordResetRequestService {




    /**
     * Create Password Reset Request
     * @param sysUserDTO
     * @return {@link ResponseDTO}
     */
    ResponseDTO<?> createPasswordResetRequest(SysUserDTO sysUserDTO);

    /**
     * Reset Password
     * @param requestId
     * @return {@link ResponseDTO}
     */
    ResponseDTO<?> resetPassword(Long requestId);

    /**
     * Get password Reset Requests For Data Table
     *
     * @param dataTableRequestDTO
     * @return {@link DataTableResponseDTO}
     */
    DataTableResponseDTO getPasswordResetRequestForDataTable(DataTableRequestDTO dataTableRequestDTO);

}
