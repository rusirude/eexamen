package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.PasswordResetRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : rusiru on 7/6/19.
 */

@Controller
public class ForgotPasswordController {


    private PasswordResetRequestService passwordResetRequestService;

    @Autowired
    public ForgotPasswordController(PasswordResetRequestService passwordResetRequestService) {
        this.passwordResetRequestService = passwordResetRequestService;
    }

    @RequestMapping(path = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotPassword() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forgotPassword");
        return mv;
    }


    @RequestMapping(path = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO<?> resetPassword(@RequestBody SysUserDTO sysUserDTO) {
        return passwordResetRequestService.createPasswordResetRequest(sysUserDTO);
    }
}