package com.lcw.herakles.platform.system.config.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.dto.annotation.OnValid;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.exception.BizServiceException;
import com.lcw.herakles.platform.system.config.dto.ConfigDto;
import com.lcw.herakles.platform.system.config.dto.req.CfgModReqDto;
import com.lcw.herakles.platform.system.config.dto.req.CfgSearchDto;
import com.lcw.herakles.platform.system.config.enums.ECfgType;
import com.lcw.herakles.platform.system.config.service.ConfigService;

/**
 * @author chenwulou
 *
 */
@Controller
@RequestMapping("system/config")
public class ConfigController extends BaseController {

	@Autowired
	private ConfigService configService;

	@RequestMapping(value = "management", method = RequestMethod.GET)
	// @RequiresPermissions(value = "system:config:management")
	public String buildConfigBasicInfo(Model model) {
		return "system/config/cfg_mgt";
	}

	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	// @RequiresPermissions(value = "system:config:list")
	public DataTablesResponseDto<ConfigDto> search(@RequestBody CfgSearchDto searchDto) {
		List<ECfgType> inCfgTypeList = Arrays.asList(ECfgType.BIZ, ECfgType.CFG);
		return configService.search(searchDto, inCfgTypeList);
	}

	@ResponseBody
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	// @RequiresPermissions(value = "system:config:modify")
	public ResultDto modify(@OnValid @RequestBody CfgModReqDto reqDto) {
		try {
			configService.modify(reqDto);
		} catch (BizServiceException ex) {
			return ResultDtoFactory.toNack(ex.getError());
		}
		return ResultDtoFactory.toAck(getMessage(COMMON_OPERATE_SUCCESS));
	}

}
