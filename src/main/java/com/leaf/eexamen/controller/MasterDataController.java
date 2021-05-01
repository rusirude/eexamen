package com.leaf.eexamen.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leaf.eexamen.dto.MasterDataDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.MasterDataService;

@Controller
@RequestMapping(path = "/masterData")
public class MasterDataController {
	
	private MasterDataService masterDataService;	
	
	@Autowired
	public MasterDataController(MasterDataService masterDataService) {		
		this.masterDataService = masterDataService;
	}

	@PreAuthorize("hasRole('ROLE_MASTERDATA')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewMasterData() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("masterData");
		return mv;
	}

	@PreAuthorize("hasRole('ROLE_MASTERDATA')")
	@RequestMapping(path = "/loadRefDataForMasterData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadMasterDataReferenceData() {
		return masterDataService.getReferenceDataForMasterData();
	}
	
	@PreAuthorize("hasRole('ROLE_MASTERDATA')")
	@RequestMapping(path = "/loadMasterData", method = RequestMethod.POST)
	@ResponseBody
	ResponseDTO<List<MasterDataDTO>> loadMasterData() {
		return masterDataService.loadAllMasterData();
	}
	
	@PreAuthorize("hasRole('ROLE_MASTERDATA')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	ResponseDTO<MasterDataDTO> saveMasterData(@RequestBody List<MasterDataDTO> masterDataDTOs) {
		return masterDataService.saveOrUpdateMasterData(masterDataDTOs);
	}

}
