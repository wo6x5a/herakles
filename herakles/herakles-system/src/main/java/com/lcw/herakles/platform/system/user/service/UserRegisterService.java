package com.lcw.herakles.platform.system.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.service.BaseService;
import com.lcw.herakles.platform.system.user.dto.req.UserRegisterReqDto;
import com.lcw.herakles.platform.system.user.entity.UserPo;
import com.lcw.herakles.platform.system.user.repository.UserRepository;

/**
 * 用户注册服务.
 * 
 * @author chenwulou
 *
 */
@Service
public class UserRegisterService extends BaseService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void register(UserRegisterReqDto req) {
        UserPo po = ConverterService.convert(req, UserPo.class);
        super.setCreateInfo(po);
        userRepository.save(po);
    }

}
