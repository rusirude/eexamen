package com.leaf.eexamen.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.SysUserSysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.SysUserSysRoleService;

@Controller
@RequestMapping(path = "/sysUserSysRole")
public class SysUserSysRoleController {
	
	private SysUserSysRoleService sysUserSysRoleService;

	@Autowired
	public SysUserSysRoleController(SysUserSysRoleService sysUserSysRoleService) {
		this.sysUserSysRoleService = sysUserSysRoleService;
	}
	
	@PreAuthorize("hasRole('ROLE_USERROLE')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewSysUserSysRole() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sysUserSysRole");
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_USERROLE')")
	@RequestMapping(path = "/loadRefDataForSysUserSysRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadSysUserSysRoleRefereceData() {
		return sysUserSysRoleService.getReferenceDataForSysUserSysRole();
	}

		
	@PreAuthorize("hasRole('ROLE_USERROLE')")
	@RequestMapping(path = "/loadSysRolesForSysUser", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadSysRolesForSysUser(@RequestBody SysUserDTO sysUserDTO) {
		return sysUserSysRoleService.getSysUserSysRoleForSysUser(sysUserDTO);
	}		

	@PreAuthorize("hasRole('ROLE_USERROLE')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<SysUserSysRoleDTO> saveSysUserSysRole(@RequestBody SysUserSysRoleDTO sysUserSysRoleDTO) {
		return sysUserSysRoleService.saveSysUserSysRole(sysUserSysRoleDTO);
	}

	@PreAuthorize("hasRole('ROLE_USERROLE')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<SysUserSysRoleDTO> deleteSysUserSysRole(@RequestBody SysUserSysRoleDTO sysUserSysRoleDTO) {
		return sysUserSysRoleService.deleteSysUserSysRole(sysUserSysRoleDTO);
	}
	
}
