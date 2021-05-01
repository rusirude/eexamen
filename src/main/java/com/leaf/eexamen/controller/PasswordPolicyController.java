package com.leaf.eexamen.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : rusiru on 7/13/19.
 */

@Controller
@RequestMapping(path = "/passwordPolicy")
public class PasswordPolicyController {

    @PreAuthorize("hasRole('ROLE_PPOLICY')")
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView viewPasswordPolicy() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("passwordPolicy");
        return mv;
    }
}
