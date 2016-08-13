package com.lcw.herakles.platform.system.dict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.system.dict.util.SystemDictUtil;

/**
 * @author chenwulou
 *
 */
@Controller
@RequestMapping(value = "system/dict")
public class DictController {

	@ResponseBody
	@RequestMapping(value = "refresh", method = RequestMethod.POST)
	public ResultDto refreshCommonDicts() {
		SystemDictUtil.initAndRefresh();
		return ResultDtoFactory.toAck("刷新成功");
	}
}
