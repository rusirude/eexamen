package com.leaf.eexamen.serviceImpl;

import java.util.*;
import java.util.stream.Collectors;

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

import com.leaf.eexamen.dao.SysRoleAuthorityDAO;
import com.leaf.eexamen.dao.SysUserAuthorityDAO;
import com.leaf.eexamen.dao.SysUserDAO;
import com.leaf.eexamen.entity.SysRoleEntity;
import com.leaf.eexamen.entity.SysUserEntity;
import com.leaf.eexamen.enums.DefaultStatusEnum;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	private SysUserDAO sysUserDAO;
	private SysRoleAuthorityDAO sysRoleAuthorityDAO;
	private SysUserAuthorityDAO sysUserAuthorityDAO;
	

	@Autowired
	public CustomUserDetailService(SysUserDAO sysUserDAO,SysRoleAuthorityDAO sysRoleAuthorityDAO,SysUserAuthorityDAO sysUserAuthorityDAO) {		
		this.sysUserDAO = sysUserDAO;
		this.sysRoleAuthorityDAO = sysRoleAuthorityDAO;
		this.sysUserAuthorityDAO = sysUserAuthorityDAO;
	}



	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			SysUserEntity sysUserEntity = sysUserDAO.getSysUserEntityByUsername(username);

			if(sysUserEntity ==null)
				throw new BadCredentialsException("User is Not Found");
			if(! DefaultStatusEnum.ACTIVE.getCode().equals(sysUserEntity.getStatusEntity().getCode()))
				throw new BadCredentialsException("User is Not Activated");

			return new User(sysUserEntity.getUsername(),sysUserEntity.getPassword(),getGrantedAuthoritiesForUser(sysUserEntity));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private List<GrantedAuthority> getGrantedAuthoritiesForUser(SysUserEntity user) {
        
        Map<String, SimpleGrantedAuthority> authorityMap = new HashMap<>();
        List<SysRoleEntity> sysRoles = new ArrayList<>();
        user.getSysUserSysRoleEntities()
                .forEach(sysUserSysRole -> {
                    sysRoles.add(sysUserSysRole.getSysRoleEntity());
                });
        if (! sysRoles.isEmpty()) {
            sysRoleAuthorityDAO.getSysRoleAuthorityEntitiesBySysRolesAndAnuthorityStatusAndSysRoleStatus(sysRoles,DefaultStatusEnum.ACTIVE.getCode(),DefaultStatusEnum.ACTIVE.getCode())
                    .stream()
                    .forEach(roleAuthority -> {
                    	String key = roleAuthority.getAuthorityEntity().getAuthCode();
                    	if(! authorityMap.containsKey(key)){
                    		authorityMap.put(key, new SimpleGrantedAuthority(key));
                    	}                    	
                    });
        }
        
        sysUserAuthorityDAO.getSysUserAuthorityEntitiesBySysUser(user.getUsername())
        	.stream()
        	.forEach(userAuthority -> {
        		String key = userAuthority.getAuthorityEntity().getAuthCode();
        		long isEnabled = userAuthority.getIsGrant();
        		if(isEnabled == 1) {
                	if(! authorityMap.containsKey(key)){
                		authorityMap.put(key, new SimpleGrantedAuthority(key));
                	}  
        		}
        		else {
        			if(authorityMap.containsKey(key)){
                		authorityMap.remove(key);
                	}  
        		}
 
        	});

        return authorityMap.values().stream().collect(Collectors.toList());
}

}
