package com.lcw.herakles.platform.system.security.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.CredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.dto.annotation.OnValid;
import com.lcw.herakles.platform.common.util.web.WebUtil;
import com.lcw.herakles.platform.common.validation.ValidateException;
import com.lcw.herakles.platform.system.security.KaptchaSupport;
import com.lcw.herakles.platform.system.security.SecurityContext;
import com.lcw.herakles.platform.system.security.dto.req.SignInDto;

/**
 * Class Name: AuthController Description: authentication controller
 * 
 * @author chenwulou
 * 
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private transient KaptchaSupport kaptchaSupport;

	/**
	 * Description: render login page
	 *
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "auth/login";
	}

	/**
	 * Description: login interface
	 *
	 * @param signInDto
	 * @param result
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login/authc", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto doLogin(@OnValid @RequestBody SignInDto signInDto, BindingResult result, HttpSession session) {
		if (!kaptchaSupport.validateCaptcha(signInDto.getCaptcha(), session)) {
			result.rejectValue("captcha", "error.invalid.captcha");
		}
		if (result.hasErrors()) {
			throw new ValidateException(result);
		}
		try {
			SecurityContext.login(signInDto.getUserName(), signInDto.getPassword(), signInDto.isRememberMe());
		} catch (AccountException e) {
			result.rejectValue("userName", "error.invalid.username");
		} catch (CredentialsException e) {
			result.rejectValue("password", "error.invalid.password");
		}
		if (result.hasErrors()) {
			throw new ValidateException(result);
		}
		return ResultDtoFactory.toRedirect(WebUtil.getFullUrlBasedOn("/web/product/view"));
	}

	/**
	 * Description: render captcha
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		kaptchaSupport.captcha(request, response);
	}

}
