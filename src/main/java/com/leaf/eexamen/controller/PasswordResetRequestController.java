package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.PasswordResetRequestDTO;
import com.leaf.eexamen.dto.common.DataTableRequestDTO;
import com.leaf.eexamen.dto.common.DataTableResponseDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.PasswordResetRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/passwordResetRequest")
public class PasswordResetRequestController {

    private PasswordResetRequestService passwordResetRequestService;

    @Autowired
    public PasswordResetRequestController(PasswordResetRequestService passwordResetRequestService) {
        this.passwordResetRequestService = passwordResetRequestService;
    }

    @PreAuthorize("hasRole('ROLE_PASSREREQ')")
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView viewCountry() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("passwordResetRequest");
        return mv;
    }

    @PreAuthorize("hasRole('ROLE_PASSREREQ')")
    @RequestMapping(path = "/loadPasswordResetRequests", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponseDTO loadSectionDataGrid(@RequestBody DataTableRequestDTO dataTableRequestDTO) {
        return passwordResetRequestService.getPasswordResetRequestForDataTable(dataTableRequestDTO);
    }


    @PreAuthorize("hasRole('ROLE_PASSREREQ')")
    @RequestMapping(path = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO<?> confirmResetPassword(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO) {
        return passwordResetRequestService.resetPassword(passwordResetRequestDTO.getId());
    }


}
