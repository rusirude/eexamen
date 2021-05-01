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

import com.leaf.eexamen.dto.TitleDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.TitleService;

@Controller
@RequestMapping(path = "/title")
public class TitleController {

	private TitleService titleService;

	@Autowired
	public TitleController(TitleService titleService) {
		this.titleService = titleService;
	}

	@PreAuthorize("hasRole('ROLE_TITLE')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView viewTitle() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("title");
		return mv;
	}

	@PreAuthorize("hasRole('ROLE_TITLE')")
	@RequestMapping(path = "/loadRefDataForTitle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<HashMap<String, Object>> loadTitleRefereceData() {
		return titleService.getReferenceDataForTitle();
	}

	@PreAuthorize("hasRole('ROLE_TITLE')")
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> saveTitle(@RequestBody TitleDTO titleDTO) {
		return titleService.saveTitle(titleDTO);
	}

	@PreAuthorize("hasRole('ROLE_TITLE')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> updateTitle(@RequestBody TitleDTO titleDTO) {
		return titleService.updateTitle(titleDTO);
	}

	@PreAuthorize("hasRole('ROLE_TITLE')")
	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<?> daleteTitle(@RequestBody TitleDTO titleDTO) {
		return titleService.deleteTitle(titleDTO);
	}

	@PreAuthorize("hasRole('ROLE_TITLE')")
	@RequestMapping(path = "/loadTitles", method = RequestMethod.POST)
	@ResponseBody
	public DataTableResponseDTO loadTitleDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
		return titleService.getTitlesForDataTable(dataTableRequestDTO);
	}

	@PreAuthorize("hasRole('ROLE_TITLE')")
	@RequestMapping(path = "/loadTitleByCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDTO<TitleDTO> loadTitleByCode(@RequestBody TitleDTO titleDTO) {
		return titleService.findTitle(titleDTO);
	}
}
