package com.lcw.herakles.platform.demo.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.util.ErrorUtils;
import com.lcw.herakles.platform.demo.dto.req.ProductReqDto;
import com.lcw.herakles.platform.demo.entity.ProductPo;
import com.lcw.herakles.platform.demo.repository.ProductRepository;
import com.lcw.herakles.platform.system.security.SecurityContext;

/**
 * Class Name: ProductService Description: TODO
 * 
 * @author chenwulou
 *
 */
@Service
public class ProductService {

	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private ProductRepository productRepository;

	// @Cacheable(value = "productCache", key = "'PROD_' + #id")
	@Transactional(readOnly = true)
	public ProductPo findProductById(String id) {
		return productRepository.findOne(id);
	}

	// @CacheEvict(value = "productCache", key = "'PROD_' + #productDto.id")
	@Transactional
	public ProductPo saveProduct(ProductReqDto productReqDto) {
		ProductPo product = new ProductPo();
		if (StringUtils.isNotBlank(productReqDto.getId())) {
			product = productRepository.findOne(productReqDto.getId());
			product.setLastMntOpId(securityContext.getCurrentOperatorId());
			product.setLastMntTs(new Date());
		}
		product = ConverterService.convert(productReqDto, product);
		return productRepository.save(product);
	}

	// @CacheEvict(value = "productCache", key = "'PROD_' + #id")
	@Transactional
	public void deleteProduct(String id) {
		if (StringUtils.isBlank(id)) {
			ErrorUtils.throwBizException(EErrorCode.COMM_ERROR_HINTS, "id不能为空");
		}
		productRepository.delete(id);
	}

}
