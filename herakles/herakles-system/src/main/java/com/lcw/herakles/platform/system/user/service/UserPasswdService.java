package com.lcw.herakles.platform.system.user.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcw.herakles.platform.system.security.SecurityContext;
import com.lcw.herakles.platform.system.security.util.EncryptPwdUtils;
import com.lcw.herakles.platform.system.user.entity.UserPo;
import com.lcw.herakles.platform.system.user.repository.UserRepository;

/**
 * 用户密码相关服务.
 * 
 * @author chenwulou
 *
 */
@Service
public class UserPasswdService {

	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 密码加密.
	 * 
	 * @param userId
	 */
	@Transactional
	public void encryptUserPwd(String userId) {
		UserPo po = userRepository.findOne(userId);
		String password = po.getPassword();
		if (EncryptPwdUtils.isPasswordPlainText(password)) {
			String encryptPwd = EncryptPwdUtils.encryptPassword(password);
			po.setPassword(encryptPwd);
			po.setLastMntOpId(securityContext.getCurrentOperatorId());
			po.setLastMntTs(new Date());
			userRepository.save(po);
		}
	}

}
