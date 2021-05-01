package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.StatusDAO;
import com.leaf.eexamen.dao.SysUserDAO;
import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.entity.SysUserEntity;
import com.leaf.eexamen.enums.ResponseCodeEnum;
import com.leaf.eexamen.service.ProfileService;
import com.leaf.eexamen.utility.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class ProfileServiceImpl implements ProfileService {

    private SysUserDAO sysUserDAO;
    private StatusDAO statusDAO;

    private CommonMethod commonMethod;

    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public ProfileServiceImpl(SysUserDAO sysUserDAO, StatusDAO statusDAO, CommonMethod commonMethod, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.sysUserDAO = sysUserDAO;
        this.statusDAO = statusDAO;
        this.commonMethod = commonMethod;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public ResponseDTO<HashMap<String, Object>> getReferenceDataForProfile() {
        HashMap<String, Object> map = new HashMap<>();
        String code = ResponseCodeEnum.FAILED.getCode();
        try {

            map.put("username", "");

            code = ResponseCodeEnum.SUCCESS.getCode();
        } catch (Exception e) {
            System.err.println("Profile Ref Data Issue");
        }
        return new ResponseDTO<>(code, map);
    }

    @Override
    @Transactional
    public ResponseDTO<?> saveProfile(SysUserDTO sysUserDTO) {
        String code = ResponseCodeEnum.FAILED.getCode();
        String description = "Password Change is Faild";
        SysUserEntity sysUserEntity;
        try {
            sysUserEntity = sysUserDAO.getSysUserEntityByUsername(commonMethod.getUsername());

            if(bCryptPasswordEncoder.matches(sysUserDTO.getPassword(),sysUserEntity.getPassword())){
                sysUserEntity.setPassword(bCryptPasswordEncoder.encode(sysUserDTO.getNewPassword()));

                commonMethod.getPopulateEntityWhenUpdate(sysUserEntity);

                sysUserDAO.updateSysUserEntity(sysUserEntity);

                code = ResponseCodeEnum.SUCCESS.getCode();
                description = "Password is changed Successfully";

            }

            else {
                description = "Current Password Invalid";
            }
        }
        catch(Exception e) {
            System.err.println("Profile Change Issue");
        }
        return new ResponseDTO<>(code,description);
    }
}
