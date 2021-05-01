package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.EmailBodyDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.EmailBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/email")
public class EmailCreatorController {

    private EmailBodyService emailBodyService;

    @Autowired
    public EmailCreatorController(EmailBodyService emailBodyService) {
        this.emailBodyService = emailBodyService;
    }

    @PreAuthorize("hasRole('ROLE_EMAIL')")
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView viewCity() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("emailBody");
        return mv;
    }

    @PreAuthorize("hasRole('ROLE_EMAIL')")
    @RequestMapping(path = "/loadAllEmail", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<EmailBodyDTO>> loadEmailData() {
        return emailBodyService.findAllEmailBody();
    }

    @PreAuthorize("hasRole('ROLE_EMAIL')")
    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<EmailBodyDTO> saveEmail(@RequestBody EmailBodyDTO emailBodyDTO) {
        return emailBodyService.updateEmailBody(emailBodyDTO);
    }
}
