package com.lcw.herakles.platform.bizlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcw.herakles.platform.bizlog.dto.BizLogDto;
import com.lcw.herakles.platform.bizlog.dto.req.BizLogQueryDto;
import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.bizlog.service.BizLogService;
import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.exception.BizServiceException;

/**
 * @author chenwulou
 *
 */
@Controller
@RequestMapping(value = "system/log")
public class BizLogController extends BaseController {

    @Autowired
    private BizLogService bizLogService;

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("logTypes", getStaticOptions(EOptType.class, true));
        return "system/log/log_list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public DataTablesResponseDto<BizLogDto> search(@RequestBody BizLogQueryDto request) {
        DataTablesResponseDto<BizLogDto> resp = bizLogService.searchLog(request);
        return resp;
    }

    @RequestMapping(value = "details")
    @ResponseBody
    public ResultDto getDetails(@RequestParam(value = "logId") String logId) {
        BizLogDto resp = null;
        try {
            resp = bizLogService.getLog(logId);
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getMessage());
        }
        return ResultDtoFactory.toAck(COMMON_SUBMIT_SUCCESS, resp);
    }

}
