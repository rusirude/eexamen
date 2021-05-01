package com.leaf.eexamen.service;

import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;

import java.util.HashMap;

public interface ProfileService {
    ResponseDTO<HashMap<String, Object>> getReferenceDataForProfile();

    ResponseDTO<?> saveProfile(SysUserDTO SysUserDTO);
}
