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

import com.leaf.eexamen.dto.SysRoleDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.SysRoleService;

@Controller
@RequestMapping(path = "/sysRole")
public class SysRoleController {

	private SysRoleService sysRoleService;

	@Autowired
	public SysRoleController(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@PreAuthorize("hasRole('ROLE_ROLE')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewSysRole() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("sysRole");
		return mv;
	}

	@PreAuthorize("hasRole('ROLE_ROLE')")
	@RequestMapping(path = "/loadRefDataForSysRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadSysRoleRefereceData() {
		return sysRoleService.getReferenceDataForSysRole();
	}

	@PreAuthorize("hasRole('ROLE_ROLE')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
		return sysRoleService.saveSysRole(sysRoleDTO);
	}

	@PreAuthorize("hasRole('ROLE_ROLE')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
		return sysRoleService.updateSysRole(sysRoleDTO);
	}

	@PreAuthorize("hasRole('ROLE_ROLE')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> daleteSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
		return sysRoleService.deleteSysRole(sysRoleDTO);
	}

	@PreAuthorize("hasRole('ROLE_ROLE')")
	@RequestMapping(path = "/loadSysRoles", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadSysRoleDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return sysRoleService.getSysRolesForDataTable(dataTableRequestDTO);
	}

	@PreAuthorize("hasRole('ROLE_ROLE')")
	@RequestMapping(path = "/loadSysRoleByCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<SysRoleDTO> loadSysRoleByCode(@RequestBody SysRoleDTO sysRoleDTO) {
		return sysRoleService.findSysRole(sysRoleDTO);
	}
}
