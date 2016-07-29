package com.lcw.herakles.platform.system.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.system.log.dto.LogDto;
import com.lcw.herakles.platform.system.log.dto.req.LogQueryDto;
import com.lcw.herakles.platform.system.log.service.LogService;

@Controller
@RequestMapping(value = "system/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;

	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String home(Model model) {
		// model.addAttribute("categoryList",
		// EnumHelper.inspectConstants(EProductCagetory.class));
		return "system/log/log_list";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public DataTablesResponseDto<LogDto> search(@RequestBody LogQueryDto request) {
		DataTablesResponseDto<LogDto> resp = logService.getDatatableLogs(request);
		return resp;
	}
}
