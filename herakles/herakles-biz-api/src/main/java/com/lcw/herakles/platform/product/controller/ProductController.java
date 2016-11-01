package com.lcw.herakles.platform.product.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.controller.BaseController;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.dto.annotation.OnValid;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.enums.EnumOption;
import com.lcw.herakles.platform.common.exception.BizServiceException;
import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.common.util.web.WebUtil;
import com.lcw.herakles.platform.product.dto.ProductDto;
import com.lcw.herakles.platform.product.dto.req.ProductReqDto;
import com.lcw.herakles.platform.product.dto.req.ProductSearchDto;
import com.lcw.herakles.platform.product.dto.req.ProductReqDto.CreateProduct;
import com.lcw.herakles.platform.product.enums.EProductCagetory;
import com.lcw.herakles.platform.product.service.ProductInfoExcelExportService;
import com.lcw.herakles.platform.product.service.ProductQueryService;
import com.lcw.herakles.platform.product.service.ProductService;

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
    private ProductService productService;
    @Autowired
    private ProductQueryService productQueryService;
    @Autowired
    private ProductInfoExcelExportService productInfoExcelExportService;

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

    @RequestMapping(value = "export-xls", method = RequestMethod.POST)
    public ModelAndView exportBrokerInvesterXls(ProductSearchDto request) {
        List<ProductDto> dataList = productQueryService.searchProduct(request).getData();
        String fileName = "test.xls";
        String tempPath = "excel/report/product_repo.xls";
        Map<String, Object> map = Maps.newHashMap();
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
