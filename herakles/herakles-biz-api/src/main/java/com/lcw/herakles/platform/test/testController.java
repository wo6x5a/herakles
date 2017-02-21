package com.lcw.herakles.platform.test;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;

@Controller
@RequestMapping(value = "test")
public class testController extends BaseController{

  @RequestMapping(value = "view", method = RequestMethod.GET)
  public String home(Model model) {
      return "test/count24";
  }
  
  @RequestMapping(value = "count24", method = RequestMethod.POST)
  @ResponseBody
  public ResultDto add(@RequestParam("number")String number) {
      String[] numberList = StringUtils.splitByWholeSeparator(number, ",");
      Integer n1 = Integer.valueOf(numberList[0]);
      Integer n2 = Integer.valueOf(numberList[1]);
      Integer n3 = Integer.valueOf(numberList[2]);
      Integer n4 = Integer.valueOf(numberList[3]);
      TfUtils tf = new TfUtils();
      tf.calculate(n1, n2, n3, n4);
      return ResultDtoFactory.toAck(getMessage(COMMON_SUBMIT_SUCCESS), tf.getExpr());
  }
}
