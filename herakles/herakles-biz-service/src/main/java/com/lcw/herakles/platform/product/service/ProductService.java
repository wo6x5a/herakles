package com.lcw.herakles.platform.product.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.bizlog.service.BizLogService;
import com.lcw.herakles.platform.common.cache.util.CacheUtil;
import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.service.BaseService;
import com.lcw.herakles.platform.common.util.ErrorUtil;
import com.lcw.herakles.platform.product.dto.req.ProductReqDto;
import com.lcw.herakles.platform.product.entity.ProductPo;
import com.lcw.herakles.platform.product.repository.ProductRepository;
import com.lcw.herakles.platform.system.security.SecurityContext;

/**
 * Class Name: ProductService Description: TODO
 * 
 * @author chenwulou
 *
 */
@Service
public class ProductService extends BaseService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BizLogService bizLogService;

    @Cacheable(value = CacheUtil.CACHE_PRODUCT, key = "'PRODUCT_' + #id")
    @Transactional(readOnly = true)
    public ProductPo findProductById(String id) {
        if (StringUtils.isBlank(id)) {
            ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "id不能为空");
        }
        return productRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<ProductPo> findProductByName(String name) {
        if (StringUtils.isBlank(name)) {
            ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "name不能为空");
        }
        return productRepository.findByName(name);
    }

    @CachePut(value = CacheUtil.CACHE_PRODUCT, key = "'PRODUCT_' + #productReqDto.id")
    @Transactional
    public ProductPo saveProduct(ProductReqDto productReqDto) {
        ProductPo product = new ProductPo();
        boolean isEdit = StringUtils.isNotBlank(productReqDto.getId());
        ProductPo oldPo = new ProductPo();
        if (isEdit) {
            product = productRepository.findOne(productReqDto.getId());
            oldPo = ConverterService.convert(product, oldPo);
            super.setUpdateInfo(product);
        }
        product = ConverterService.convert(productReqDto, product);

        // add bizlog
        if (isEdit) {
            bizLogService.saveEditLog(ProductPo.class, EOptType.UPDATE, "修改product",
                    productReqDto.getId(), SecurityContext.getInstance().getCurrentOperatorId(),
                    oldPo, product, "");
        }

        super.setCreateInfo(product);
        return productRepository.save(product);

    }

    @CacheEvict(value = CacheUtil.CACHE_PRODUCT, key = "'PRODUCT_' + #id", beforeInvocation = true)
    @Transactional
    public void deleteProduct(String id) {
        if (StringUtils.isBlank(id)) {
            ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "id不能为空");
        }
        productRepository.delete(id);
    }

    /**
     * 清空PRODUCT_CACHE所有缓存
     */
    @CacheEvict(value = CacheUtil.CACHE_PRODUCT, allEntries = true)
    public void clearAllProductCache() {}

}
