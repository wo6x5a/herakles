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

import javax.validation.groups.Default;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.cache.redis.repository.BaseRedisDao;
import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.constant.FileConsts;
import com.lcw.herakles.platform.common.constant.FileTemplateConsts;
import com.lcw.herakles.platform.common.constant.FtpFileConsts;
import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.dto.annotation.OnValid;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.enums.EnumOption;
import com.lcw.herakles.platform.common.exception.BizServiceException;
import com.lcw.herakles.platform.common.util.DateUtils;
import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.common.util.file.ftp.FtpUtil;
import com.lcw.herakles.platform.common.util.http.HttpClientUtil;
import com.lcw.herakles.platform.common.util.web.WebUtil;
import com.lcw.herakles.platform.demo.dto.ProductDto;
import com.lcw.herakles.platform.demo.dto.req.ProductReqDto;
import com.lcw.herakles.platform.demo.dto.req.ProductReqDto.CreateProduct;
import com.lcw.herakles.platform.demo.dto.req.ProductSearchDto;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;
import com.lcw.herakles.platform.demo.service.ProductInfoExcelExportService;
import com.lcw.herakles.platform.demo.service.ProductQueryService;
import com.lcw.herakles.platform.demo.service.ProductService;
import com.lcw.herakles.platform.system.mail.service.EmailSerivce;
import com.lcw.herakles.platform.system.security.SecurityContext;

/**
 * Class Name: ProductController Description
 * 
 * @author chenwulou
 * 
 */
@Controller
@RequestMapping(value = "biz/product")
public class ProductController extends BaseController {

    @Autowired
    private BaseRedisDao baseRedisDao;
    @Autowired
    private EmailSerivce emailSerivce;
    @Autowired
    private ProductService productService;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private ProductQueryService productQueryService;
    @Autowired
    private ProductInfoExcelExportService productInfoExcelExportService;
    

    @RequestMapping(value = "http-client-test", method = RequestMethod.GET)
    public String httpClientTest(){
        String url = "http://127.0.0.1:8081/herakles-web/web/biz/product/delete";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "4b74866f2e8e42578f3a32a6f9bf8324");
        HttpClientUtil.post(url, params);
        return "biz/product/test";
    }

    @ResponseBody
    @RequestMapping(value = "upload-image", method = RequestMethod.POST)
    public ResultDto uploadImage(@RequestParam MultipartFile file) {
        String resp = null;
        StringBuilder filePath = new StringBuilder("");
        filePath.append(FtpFileConsts.TP_PIC);
        filePath.append(FtpFileConsts.PG_PRODUCT);
        try {
            resp = FtpUtil.upload(FtpUtil.rename(file.getName()), file.getInputStream(), filePath.toString());
        } catch (IOException e) {
            ResultDtoFactory.toNack(e.getMessage());
        }
        return ResultDtoFactory.toAck("上传成功", resp);
    }

    @RequestMapping(value = "ftp-test", method = RequestMethod.GET)
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
        @SuppressWarnings("unused")
        String resp = FtpUtil.upload(FtpUtil.rename(file.getName()), in, filePath.toString());
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

    @RequestMapping("/longtimetask3")
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

    @RequestMapping(value = "test-email", method = RequestMethod.GET)
    public String testEmail() {
        String title = "邮箱绑定";
        String templateName = "modifyEmail";
        Map<String, Object> model = new HashMap<String, Object>();
        String expireDate = DateUtils.formatDate(
                DateUtils.getDate(this.getDefaultExpireDttm(), "yyyyMMddHHmmss"),
                "yyyy-MM-dd hh:mm:ss");
        model.put("expireDate", expireDate);
        model.put("nickName", securityContext.getCurrentUser().getNickName());
        model.put("url", "www.bing.com");
        model.put("isUnbound", "xxxxx");
        emailSerivce.sendSimpleEmail("标题", "内容", "wo6x5a3@163.com");
        emailSerivce.sendHtmlEmail(title, templateName, model, "wo6x5a3@163.com");
        return "biz/product/test";
    }

    private String getDefaultExpireDttm() {
        Date expireDate = DateUtils.add(new Date(), Calendar.HOUR_OF_DAY, 24);
        return DateUtils.formatDate(expireDate, "yyyyMMddHHmmss");
    }

    @RequestMapping(value = "test-temp-form", method = RequestMethod.GET)
    public ModelAndView printApplicationForm() {
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

    /**
     * Description: render home page
     *
     * @param model
     * @return
     */
    @RequiresPermissions("product:view")
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("categoryList", getStaticOptions(EProductCagetory.class, true));
        // model.addAttribute("categoryList", this.getProductCagetory());
        return "biz/product/product_list";
    }

    /**
     * Description: search product list on the home page
     *
     * @param request
     * @return
     */
    // @RequiresPermissions("product:list")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public DataTablesResponseDto<ProductDto> search(@RequestBody ProductSearchDto request) {
        DataTablesResponseDto<ProductDto> resp = productQueryService.searchProduct(request);
        return resp;
    }

    @RequestMapping(value = "/export-xls", method = RequestMethod.POST)
    public ModelAndView exportBrokerInvesterXls(ProductSearchDto request) {
        List<ProductDto> dataList = productQueryService.searchProduct(request).getData();
        String fileName = "test.xls";
        String tempPath = "excel/report/product_repo.xls";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApplicationConsts.REPORT_DATA, dataList);
        map.put(ApplicationConsts.REPORT_FILE_NAME, fileName);
        map.put(ApplicationConsts.REPORT_TEMP_PATH, tempPath);
        return new ModelAndView(productInfoExcelExportService, map);
    }

    /**
     * Description: render add-product page
     *
     * @param model
     * @return
     */
    // @RequiresPermissions("product:add:view")
    @RequestMapping(value = "add/view", method = RequestMethod.GET)
    public String getAddPage(Model model) {
        model.addAttribute("categoryList", getStaticOptions(EProductCagetory.class, false));
        return "biz/product/product_add";
    }

    /**
     * Description: add a product
     *
     * @param dto
     * @return
     */
    // @RequiresPermissions("product:add")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto add( @RequestBody @OnValid({CreateProduct.class, Default.class}) ProductReqDto dto) {
//        validate(dto, new Class<?>[] {CreateProduct.class, Default.class});
        dto.setId(null);
        productService.saveProduct(dto);
        return ResultDtoFactory.toRedirect(
                WebUtil.getFullUrlBasedOn(ApplicationConsts.GLOBAL_CONTEXT + "biz/product/view"));
    }

    /**
     * Description: render the detail page of a product
     *
     * @param id
     * @param model
     * @return
     */
    // @RequiresPermissions("product:detatil")
    @RequestMapping(value = "detatil", method = RequestMethod.GET)
    public String detail( @RequestParam(value = "id") String id,
            Model model) {
        model.addAttribute("detail", productService.findProductById(id));
        model.addAttribute("categoryList", EnumHelper.inspectConstants(EProductCagetory.class));
        return "biz/product/product_edit";
    }

    /**
     * Description: update a product
     *
     * @param id
     * @param dto
     * @return
     */
    // @RequiresPermissions("product:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto update(@RequestBody @OnValid ProductReqDto dto) {
        productService.saveProduct(dto);
        return ResultDtoFactory.toAck(getMessage(COMMON_SAVE_SUCCESS));
    }

    /**
     * Description: delete a product
     *
     * @param id
     * @return
     */
    // @RequiresPermissions("product:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto delete(@RequestParam("id") String id) {
        try {
            productService.deleteProduct(id);
        } catch (BizServiceException ex) {
            return ResultDtoFactory.toNack(ex.getError());
        }
        return ResultDtoFactory.toAck(getMessage(COMMON_REMOVE_SUCCESS));
    }

    @SuppressWarnings("unused")
    private List<EnumOption> getProductCagetory() {
        List<EnumOption> options = Lists.newArrayList();
        options.add(
                new EnumOption(EProductCagetory.BIRDS.name(), EProductCagetory.BIRDS.getText()));
        options.add(new EnumOption(EProductCagetory.CATS.name(), EProductCagetory.CATS.getText()));
        options.add(new EnumOption(EProductCagetory.DOGS.name(), EProductCagetory.DOGS.getText()));
        return options;
    }

}
