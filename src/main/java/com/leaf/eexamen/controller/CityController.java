package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.CityDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping(path = "/city")
public class CityController {

	private CityService cityService;

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@PreAuthorize("hasRole('ROLE_CITY')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewCity() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("city");
		return mv;
	}

	@PreAuthorize("hasRole('ROLE_CITY')")
	@RequestMapping(path = "/loadRefDataForCity", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadCityReferenceData() {
		return cityService.getReferenceDataForCity();
	}

	@PreAuthorize("hasRole('ROLE_CITY')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveCity(@RequestBody CityDTO cityDTO) {
		return cityService.saveCity(cityDTO);
	}

	@PreAuthorize("hasRole('ROLE_CITY')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateCity(@RequestBody CityDTO cityDTO) {
		return cityService.updateCity(cityDTO);
	}

	@PreAuthorize("hasRole('ROLE_CITY')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> deleteCity(@RequestBody CityDTO cityDTO) {
		return cityService.deleteCity(cityDTO);
	}

	@PreAuthorize("hasRole('ROLE_CITY')")
	@RequestMapping(path = "/loadCities", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadCityDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return cityService.getCitysForDataTable(dataTableRequestDTO);
	}

	@PreAuthorize("hasRole('ROLE_CITY')")
	@RequestMapping(path = "/loadCityByCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<CityDTO> loadCityByCode(@RequestBody CityDTO cityDTO) {
		return cityService.findCity(cityDTO);
	}
}
