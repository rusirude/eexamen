package com.leaf.eexamen.controller;

import com.leaf.eexamen.dto.SysUserDTO;
import com.leaf.eexamen.dto.common.ResponseDTO;
import com.leaf.eexamen.service.ProfileService;
import com.leaf.eexamen.utility.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * @author : rusiru on 7/6/19.
 */

@Controller
public class ProfileController {


    private ProfileService profileService;
    private CommonMethod commonMethod;


    @Autowired
    public ProfileController(ProfileService profileService, CommonMethod commonMethod) {
        this.profileService = profileService;
        this.commonMethod = commonMethod;
    }

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("profile");
        mv.addObject("user",commonMethod.getUsername());
        return mv;
    }

    @RequestMapping(path = "/profileReference", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO<HashMap<String, Object>> loadSystemUserReferenceData() {
        return profileService.getReferenceDataForProfile();
    }

    @RequestMapping(path = "/profile/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO<?> saveProfile(@RequestBody SysUserDTO sysUserDTO) {
        return profileService.saveProfile(sysUserDTO);
    }
}