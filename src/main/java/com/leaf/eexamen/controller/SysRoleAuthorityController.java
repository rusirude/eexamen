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

import com.leaf.eexamen.dto.SysRoleAuthorityDTO;
import com.leaf.eexamen.dto.SysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.SysRoleAuthorityService;

@Controller
@RequestMapping(path = "/sysRoleAuthority")
public class SysRoleAuthorityController {
	
	private SysRoleAuthorityService sysRoleAuthorityService;

	@Autowired
	public SysRoleAuthorityController(SysRoleAuthorityService sysRoleAuthorityService) {
		this.sysRoleAuthorityService = sysRoleAuthorityService;
	}
	
	@PreAuthorize("hasRole('ROLE_ROLEAUTHORITY')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewSysRoleAuthority() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sysRoleAuthority");
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_ROLEAUTHORITY')")
	@RequestMapping(path = "/loadRefDataForSysRoleAuthority", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadysRoleAuthorityRefereceData() {
		return sysRoleAuthorityService.getReferenceDataForSysRoleAuthority();
	}

		
	@PreAuthorize("hasRole('ROLE_ROLEAUTHORITY')")
	@RequestMapping(path = "/loadAuthoritiesForSysRole", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadAuthoritiesForSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
		return sysRoleAuthorityService.getSysRoleAuthorityForSysRole(sysRoleDTO);
	}		

	@PreAuthorize("hasRole('ROLE_ROLEAUTHORITY')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<SysRoleAuthorityDTO> saveSysRoleAuthority(@RequestBody SysRoleAuthorityDTO sysRoleAuthorityDTO) {
		return sysRoleAuthorityService.saveSysRoleAuthority(sysRoleAuthorityDTO);
	}

	@PreAuthorize("hasRole('ROLE_ROLEAUTHORITY')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<SysRoleAuthorityDTO> deleteSysRoleAuthority(@RequestBody SysRoleAuthorityDTO sysRoleAuthorityDTO) {
		return sysRoleAuthorityService.deleteSysRoleAuthority(sysRoleAuthorityDTO);
	}
	
}
