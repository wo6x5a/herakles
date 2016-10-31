package com.lcw.herakles.platform.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lcw.herakles.platform.common.cache.redis.repository.BaseRedisDao;
import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.constant.FileConsts;
import com.lcw.herakles.platform.common.constant.FileTemplateConsts;
import com.lcw.herakles.platform.common.constant.FtpFileConsts;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.exception.BizServiceException;
import com.lcw.herakles.platform.common.util.DateUtil;
import com.lcw.herakles.platform.common.util.file.FileUtil;
import com.lcw.herakles.platform.common.util.file.ftp.FtpUtil;
import com.lcw.herakles.platform.common.util.http.HttpClientUtil;
import com.lcw.herakles.platform.system.mail.service.EmailSerivce;
import com.lcw.herakles.platform.system.security.SecurityContext;

/**
 * demo controller
 * 
 * @author chenwulou
 *
 */
@Controller
@RequestMapping(value = "biz/demo")
public class DemoController {

    @Autowired
    private BaseRedisDao baseRedisDao;
    @Autowired
    private EmailSerivce emailSerivce;
    @Autowired
    private SecurityContext securityContext;


    /**
     * http client test
     * 
     * @return
     */
    @RequestMapping(value = "http-client-test", method = RequestMethod.GET)
    public String httpClientTest() {
        String url = "http://127.0.0.1:8081/herakles-web/web/biz/product/delete";
        Map<String, String> params = Maps.newHashMap();
        params.put("id", "4b74866f2e8e42578f3a32a6f9bf8324");
        HttpClientUtil.post(url, params);
        return "biz/product/test";
    }

    /**
     * 上传图片
     * 
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "upload-image", method = RequestMethod.POST)
    public ResultDto uploadImage(@RequestParam MultipartFile file) {
        String resp = null;
        StringBuilder filePath = new StringBuilder("");
        filePath.append(FtpFileConsts.TP_PIC);
        filePath.append(FtpFileConsts.PG_PRODUCT);
        try {
            resp = FtpUtil.uploadImgWithMark(FileUtil.rename(file.getName()), file.getInputStream(),
                    filePath.toString());
        } catch (IOException e) {
            ResultDtoFactory.toNack(e.getMessage());
        }
        return ResultDtoFactory.toAck("上传成功", resp);
    }

    /**
     * ftp test
     * 
     * @return
     */
    @RequestMapping(value = "ftp-test", method = RequestMethod.GET)
    @SuppressWarnings("unused")
    public String ftpTest() {
        File file = new File("D:/test.png");
        StringBuilder filePath = new StringBuilder("");
        filePath.append(FtpFileConsts.TP_PIC);
        filePath.append(FtpFileConsts.PG_PRODUCT);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // String resp = FtpUtil.uploadFile(FileUtil.rename(file.getName()), in,
        // filePath.toString());
        String resp1 =
                FtpUtil.uploadImgWithMark(FileUtil.rename(file.getName()), in, filePath.toString());
        // FtpUtil.download("/var/ftp/pub/pic/product/147738576471683061.png", "D:/");
        // System.out.println(resp + "," + resp1);
        return "biz/product/test";
    }

    /**
     * 异步调用测试
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "longtimetask1", method = RequestMethod.GET)
    public WebAsyncTask<String> longTimeTask1() {
        System.out.println("longtimetask1 被调用 thread id : " + Thread.currentThread().getId());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                System.out.println("执行成功 thread id : " + Thread.currentThread().getId());
                return "Callable result";
            }
        };
        return new WebAsyncTask<String>(3000, callable);// 允许指定timeout时间
    }


    /**
     * 异步调用测试
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("longtimetask2")
    public Callable<String> longtimetask2(
            final @RequestParam(required = false, defaultValue = "true") boolean handled) {
        System.out.println("longtimetask2 被调用 thread id : " + Thread.currentThread().getId());
        // 进行一些与处理之后，把最耗时的业务逻辑部分放到Callable中，注意，如果你需要在new
        // Callable中用到从页面传入的参数，需要在参数前加入final
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (handled) {
                    Thread.sleep(2000);
                } else {
                    Thread.sleep(2000 * 2);
                }
                System.out.println("执行成功 thread id : " + Thread.currentThread().getId());
                return "Callable result";
            }
        };
    }


    /**
     * 异步调用测试
     * 
     * @return
     */
    @RequestMapping("longtimetask3")
    @ResponseBody
    public DeferredResult<String> longtimetask3() {
        System.out.println("longtimetask3 被调用 thread id : " + Thread.currentThread().getId());
        DeferredResult<String> deferredResult = new DeferredResult<String>();
        // try {
        // Thread.sleep(2000);
        // deferredResult.setResult("Callable result");
        // System.out.println("执行成功 thread id : " + Thread.currentThread().getId());
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // System.out.println("执行成功 thread id : " + Thread.currentThread().getId());
        // Add deferredResult to a Queue or a Map...
        return deferredResult;
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        return "biz/product/test";
    }

    /**
     * redis test
     * 
     * @return
     */
    @SuppressWarnings({"unused", "unchecked"})
    @RequestMapping(value = "test-redis", method = RequestMethod.GET)
    public String testRedis() {
        List<String> listStr = Lists.newArrayList();
        List<String> listStr1 = Lists.newArrayList();
        listStr.add("1");
        listStr.add("2");
        listStr.add("3");
        baseRedisDao.addStr("USER", "LCW", "6x5");
        baseRedisDao.getStr("USER", "LCW");
        baseRedisDao.addStr("USER", "LIST", listStr);
        listStr1 = (List<String>) baseRedisDao.getStr("USER", "LIST");
        return "biz/product/test";
    }

    /**
     * email test
     *
     * @return
     */
    @RequestMapping(value = "test-email", method = RequestMethod.GET)
    public String testEmail() {
        String title = "邮箱绑定";
        String templateName = "modifyEmail";
        Map<String, Object> model = new HashMap<String, Object>();
        String expireDate =
                DateUtil.formatDate(DateUtil.getDate(this.getDefaultExpireDttm(), "yyyyMMddHHmmss"),
                        DateUtil.YYYYMMDDHHMMSS);
        model.put("expireDate", expireDate);
        model.put("nickName", securityContext.getCurrentUser().getNickName());
        model.put("url", "www.bing.com");
        model.put("isUnbound", "xxxxx");
        try {
            emailSerivce.sendSimpleEmail("标题", "内容", "wo6x5a3@163.com");
        } catch (BizServiceException e) {
            // TODO
        }
        try {
            emailSerivce.sendHtmlEmail(title, templateName, model, "wo6x5a3@163.com");
        } catch (BizServiceException e) {
            // TODO
        }
        return "biz/product/test";
    }

    /**
     * 到期时间
     * 
     * @return
     */
    private String getDefaultExpireDttm() {
        Date expireDate = DateUtil.add(new Date(), Calendar.HOUR_OF_DAY, 24);
        return DateUtil.formatDate(expireDate, "yyyyMMddHHmmss");
    }

    /**
     * 模板下载
     * 
     * @return
     */
    @RequestMapping(value = "test-temp-form", method = RequestMethod.GET)
    public ModelAndView tempDownload() {
        ModelAndView model = new ModelAndView(FileTemplateConsts.REDIRECT);
        model.addObject(FileTemplateConsts.FILE_NAME, FileTemplateConsts.TEST_TEMP);
        try {
            model.addObject(FileTemplateConsts.FILE_SHOW_NAME,
                    URLEncoder.encode(FileTemplateConsts.TEST_TEMP_NAME, ApplicationConsts.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addObject(FileTemplateConsts.FILE_SUFFIXES, FileConsts.DOC);
        model.addObject(FileTemplateConsts.FILE_PATH, FileTemplateConsts.WORD_PATH);
        return model;
    }

}
