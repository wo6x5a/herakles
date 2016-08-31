package com.lcw.herakles.platform.demo.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.lcw.herakles.platform.common.cache.redis.repository.BaseRedisDao;
import com.lcw.herakles.platform.common.constant.ApplicationConstant;
import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.dto.annotation.OnValid;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.enums.EnumOption;
import com.lcw.herakles.platform.common.exception.BizServiceException;
import com.lcw.herakles.platform.common.util.DateUtils;
import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.common.util.web.WebUtil;
import com.lcw.herakles.platform.demo.dto.ProductDto;
import com.lcw.herakles.platform.demo.dto.ProductDto.CreateProduct;
import com.lcw.herakles.platform.demo.dto.req.ProductReqDto;
import com.lcw.herakles.platform.demo.dto.req.ProductSearchDto;
import com.lcw.herakles.platform.demo.entity.ProductPo;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;
import com.lcw.herakles.platform.demo.service.ProductInfoExcelExportService;
import com.lcw.herakles.platform.demo.service.ProductQueryService;
import com.lcw.herakles.platform.demo.service.ProductService;
import com.lcw.herakles.platform.system.files.consts.FileConsts;
import com.lcw.herakles.platform.system.files.consts.FileTemplateConsts;
import com.lcw.herakles.platform.system.mail.service.EmailSerivce;
import com.lcw.herakles.platform.system.security.SecurityContext;

/**
 * Class Name: ProductController Description
 * 
 * @author chenwulou
 * 
 */
@Controller
@RequestMapping(value = "product")
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

    /**
     * 异步调用测试
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "longtimetask1", method = RequestMethod.GET)
    public WebAsyncTask<String> longTimeTask1() {
        System.out.println("/longtimetask1 被调用 thread id : " + Thread.currentThread().getId());
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
        System.out.println("/longtimetask2 被调用 thread id : " + Thread.currentThread().getId());
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
        System.out.println("/longtimetask3 被调用 thread id : " + Thread.currentThread().getId());
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
        return "product/test";
    }

    @SuppressWarnings({"unused", "unchecked"})
    @RequestMapping(value = "test-redis", method = RequestMethod.GET)
    public String testRedis() {
        List<String> listStr = new ArrayList<>();
        List<String> listStr1 = new ArrayList<>();
        listStr.add("1");
        listStr.add("2");
        listStr.add("3");
        baseRedisDao.addStr("USER", "LCW", "6x5");
        baseRedisDao.getStr("USER", "LCW");
        baseRedisDao.addStr("USER", "LIST", listStr);
        listStr1 = (List<String>) baseRedisDao.getStr("USER", "LIST");
        return "product/test";
    }

    @SuppressWarnings("unused")
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
        emailSerivce.sendSimpleEmail("标题", "内容", "2669062184@qq.com");
        // emailSerivce.sendHtmlEmail(title, templateName, model,
        // "wo6x5a1@163.com");
        return "product/test";
    }

    private String getDefaultExpireDttm() {
        Date expireDate = DateUtils.add(new Date(), Calendar.HOUR_OF_DAY, 24);
        return DateUtils.formatDate(expireDate, "yyyyMMddHHmmss");
    }

    @RequestMapping(value = "test-temp-form", method = RequestMethod.GET)
    public ModelAndView printApplicationForm() {
        ModelAndView model = new ModelAndView(FileTemplateConsts.REDIRECT);
        model.addObject("fileName", FileTemplateConsts.TEST_TEMP);
        model.addObject("showFileName", FileTemplateConsts.TEST_TEMP_NAME);
        model.addObject("suffixes", FileConsts.DOC);
        model.addObject("filePath", FileTemplateConsts.WORD_PATH);
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
        return "product/product_list";
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
        map.put(ApplicationConstant.REPORT_DATA, dataList);
        map.put(ApplicationConstant.REPORT_FILE_NAME, fileName);
        map.put(ApplicationConstant.REPORT_TEMP_PATH, tempPath);
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
        return "product/product_add";
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
    public ResultDto add( @RequestBody @OnValid ProductReqDto dto) {
        validate(dto, new Class<?>[] {CreateProduct.class, Default.class});
        dto.setId(null);
        productService.saveProduct(dto);
        return ResultDtoFactory.toRedirect(
                WebUtil.getFullUrlBasedOn(ApplicationConstant.GLOBAL_CONTEXT + "product/view"));
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
        ProductPo detail = productService.findProductById(id);
        if (detail == null) {
            throw new BizServiceException(EErrorCode.PRODUCT_NOT_FOUND);
        }
        model.addAttribute("detail", detail);
        model.addAttribute("categoryList", EnumHelper.inspectConstants(EProductCagetory.class));
        return "product/product_edit";
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
        // id = null;
        try {
            productService.deleteProduct(id);
        } catch (BizServiceException ex) {
            return ResultDtoFactory.toNack(ex.getError());
        }
        return ResultDtoFactory.toAck(getMessage(COMMON_REMOVE_SUCCESS));
    }

    @SuppressWarnings("unused")
    private List<EnumOption> getProductCagetory() {
        List<EnumOption> options = new ArrayList<EnumOption>();
        options.add(
                new EnumOption(EProductCagetory.BIRDS.name(), EProductCagetory.BIRDS.getText()));
        options.add(new EnumOption(EProductCagetory.CATS.name(), EProductCagetory.CATS.getText()));
        options.add(new EnumOption(EProductCagetory.DOGS.name(), EProductCagetory.DOGS.getText()));
        return options;
    }

}
