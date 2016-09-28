package com.lcw.herakles.platform.demo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.service.BaseService;
import com.lcw.herakles.platform.common.util.ErrorUtil;
import com.lcw.herakles.platform.demo.dto.req.ProductReqDto;
import com.lcw.herakles.platform.demo.entity.ProductPo;
import com.lcw.herakles.platform.demo.repository.ProductRepository;

/**
 * Class Name: ProductService Description: TODO
 * 
 * @author chenwulou
 *
 */
@Service
public class ProductService extends BaseService{

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = "PRODUCT_CACHE", key = "'PRODUCT_' + #id")
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

    @CachePut(value = "PRODUCT_CACHE", key = "'PRODUCT_' + #productReqDto.id")
    @Transactional
    public ProductPo saveProduct(ProductReqDto productReqDto) {
        ProductPo product = new ProductPo();
        if (StringUtils.isNotBlank(productReqDto.getId())) {
            product = productRepository.findOne(productReqDto.getId());
            super.setUpdate(product);
        }
        product = ConverterService.convert(productReqDto, product);
        super.setCreate(product);
        return productRepository.save(product);

    }

    @CacheEvict(value = "PRODUCT_CACHE", key = "'PRODUCT_' + #id", beforeInvocation = true)
    @Transactional
    public void deleteProduct(String id) {
        if (StringUtils.isBlank(id)) {
            ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "id不能为空");
        }
        productRepository.delete(id);
    }

}
