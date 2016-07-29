package com.lcw.herakles.platform.system.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.system.user.dto.UserDto;
import com.lcw.herakles.platform.system.user.repository.UserRepository;


@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Transactional(readOnly = true)
  public UserDto findByNickName(String nickName) {
    return ConverterService.convert(userRepository.findByNickName(nickName), UserDto.class);
  }
}
