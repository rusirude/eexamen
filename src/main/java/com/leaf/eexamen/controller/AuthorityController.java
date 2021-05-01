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

import com.leaf.eexamen.dto.AuthorityDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.AuthorityService;

@Controller
@RequestMapping(path = "/authority")
public class AuthorityController {
	
	private AuthorityService authorityService;
	
	@Autowired
	public AuthorityController(AuthorityService authorityService) {		
		this.authorityService = authorityService;
	}

	@PreAuthorize("hasRole('ROLE_AUTHORITY')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewAuthority() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("authority");
		return mv;
	}
	
	@PreAuthorize("hasRole('ROLE_AUTHORITY')")
	@RequestMapping(path = "/loadRefDataForAuthority", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadAuthorityRefereceData() {
		return authorityService.getReferenceDataForAuthority();
	}	

	@PreAuthorize("hasRole('ROLE_AUTHORITY')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveAuthority(@RequestBody AuthorityDTO authorityDTO) {
		return authorityService.saveAuthority(authorityDTO);
	}

	@PreAuthorize("hasRole('ROLE_AUTHORITY')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateAuthority(@RequestBody AuthorityDTO authorityDTO) {
		return authorityService.updateAuthority(authorityDTO);
	}

	@PreAuthorize("hasRole('ROLE_AUTHORITY')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> daleteAuthority(@RequestBody AuthorityDTO authorityDTO) {
		return authorityService.deleteAuthority(authorityDTO);
	}

	@PreAuthorize("hasRole('ROLE_AUTHORITY')")
	@RequestMapping(path = "/loadAuthorities", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadAuthorityDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return authorityService.getAuthoritiesForDataTable(dataTableRequestDTO);
	}
	
	@PreAuthorize("hasRole('ROLE_AUTHORITY')")
	@RequestMapping(path = "/loadAuthorityByCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<AuthorityDTO> loadAuthorityByCode(@RequestBody AuthorityDTO authorityDTO) {
		return authorityService.findAuthority(authorityDTO);
	}
}
