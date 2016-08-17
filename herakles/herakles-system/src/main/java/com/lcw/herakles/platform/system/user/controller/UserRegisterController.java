package com.lcw.herakles.platform.system.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.system.user.dto.req.UserRegisterReqDto;
import com.lcw.herakles.platform.system.user.service.UserRegisterService;

@Controller
@RequestMapping(value = "user/register")
public class UserRegisterController extends BaseController {

    @Autowired
    private UserRegisterService userRegisterService;

    @ResponseBody
    @RequestMapping(value = "do", method = RequestMethod.POST)
    public ResultDto regist(@RequestBody UserRegisterReqDto reqDto) {
        userRegisterService.register(reqDto);
        return null;

    }

}
