package com.leaf.eexamen.serviceImpl;

import com.leaf.eexamen.dao.SysRoleAuthorityDAO;
import com.leaf.eexamen.dao.SysUserAuthorityDAO;
import com.leaf.eexamen.dao.SysUserDAO;
import com.leaf.eexamen.entity.SysRoleEntity;
import com.leaf.eexamen.entity.SysUserEntity;
import com.leaf.eexamen.enums.DefaultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailService implements UserDetailsService {

    private SysUserDAO sysUserDAO;
    private SysRoleAuthorityDAO sysRoleAuthorityDAO;
    private SysUserAuthorityDAO sysUserAuthorityDAO;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(username);

            if (sysUserEntity == null)
                throw new BadCredentialsException("User is Not Found");
            if (!DefaultStatusEnum.ACTIVE.getCode().equals(sysUserEntity.getStatusEntity().getCode()))
                throw new BadCredentialsException("User is Not Activated");

            return new User(sysUserEntity.getUsername(), sysUserEntity.getPassword(), getGrantedAuthoritiesForUser(sysUserEntity));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    private List<GrantedAuthority> getGrantedAuthoritiesForUser(SysUserEntity user) {

        Map<String, SimpleGrantedAuthority> authorityMap = new HashMap<>();
        List<SysRoleEntity> sysRoles = new ArrayList<>();
        user.getSysUserSysRoleEntities()
                .forEach(sysUserSysRole ->
                        sysRoles.add(sysUserSysRole.getSysRoleEntity())
                );
        if (!sysRoles.isEmpty()) {
            sysRoleAuthorityDAO.getSysRoleAuthorityEntitiesBySysRolesAndAnuthorityStatusAndSysRoleStatus(sysRoles, DefaultStatusEnum.ACTIVE.getCode(), DefaultStatusEnum.ACTIVE.getCode())
                    .forEach(roleAuthority -> {
                        String key = roleAuthority.getAuthorityEntity().getAuthCode();
                        if (!authorityMap.containsKey(key)) {
                            authorityMap.put(key, new SimpleGrantedAuthority(key));
                        }
                    });
        }

        sysUserAuthorityDAO.getSysUserAuthorityEntitiesBySysUser(user.getUsername())
                .forEach(userAuthority -> {
                    String key = userAuthority.getAuthorityEntity().getAuthCode();
                    long isEnabled = userAuthority.getIsGrant();
                    if (isEnabled == 1) {
                        if (!authorityMap.containsKey(key)) {
                            authorityMap.put(key, new SimpleGrantedAuthority(key));
                        }
                    } else {
                        authorityMap.remove(key);
                    }

                });

        return new ArrayList<>(authorityMap.values());
    }

}
