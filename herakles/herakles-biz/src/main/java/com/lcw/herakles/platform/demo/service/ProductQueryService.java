package com.lcw.herakles.platform.demo.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.dto.page.PageModelDto;
import com.lcw.herakles.platform.common.paging.PaginationUtil;
import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.demo.dto.ProductDto;
import com.lcw.herakles.platform.demo.dto.req.ProductBasePageSearchDto;
import com.lcw.herakles.platform.demo.dto.req.ProductSearchDto;
import com.lcw.herakles.platform.demo.entity.ProductPo;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;
import com.lcw.herakles.platform.demo.repository.ProductRepository;

/**
 * @author chenwulou
 *
 */
@Service
public class ProductQueryService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public DataTablesResponseDto<ProductDto> searchProduct(final ProductSearchDto searchDto) {

		Specification<ProductPo> specification = new Specification<ProductPo>() {
			@Override
			public Predicate toPredicate(Root<ProductPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (searchDto != null) {
				    String keyWord = StringUtils.deleteWhitespace(searchDto.getKeyword());
					if (StringUtils.isNotBlank(keyWord)) {
						expressions.add(cb.or(
								cb.like(cb.lower(root.<String> get("name")),
										"%" + keyWord.toLowerCase()),
								cb.like(cb.lower(root.<String> get("description")),
										"%" + keyWord.toLowerCase())));
					}
					if (null != searchDto.getCategory() && EProductCagetory.ALL != searchDto.getCategory()) {
						expressions.add(cb.equal(root.<EProductCagetory> get("category"),  searchDto.getCategory()));
					}
				}
				return predicate;
			}
		};
		Pageable pageRequest = PaginationUtil.buildPageRequest(searchDto);
		Page<ProductPo> pos = productRepository.findAll(specification, pageRequest);
		DataTablesResponseDto<ProductDto> result = PaginationUtil.populateFromPage(pos,
				new Function<ProductPo, ProductDto>() {
					@Override
					public ProductDto apply(ProductPo po) {
						ProductDto dto = ConverterService.convert(po, ProductDto.class);
						return dto;
					}
				}, searchDto.getEcho());
		return result;
	}

	@Transactional(readOnly = true)
	public PageModelDto<ProductDto> searchBasePageProduct(final ProductBasePageSearchDto searchDto) {
		Specification<ProductPo> specification = new Specification<ProductPo>() {
			@Override
			public Predicate toPredicate(Root<ProductPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (searchDto != null) {
					if (StringUtils.isNotBlank(searchDto.getKeyword())) {
						expressions.add(cb.or(
								cb.like(cb.lower(root.<String> get("name")),
										"%" + searchDto.getKeyword().trim().toLowerCase() + "%"),
								cb.like(cb.lower(root.<String> get("description")),
										"%" + searchDto.getKeyword().trim().toLowerCase() + "%")));
					}
					if (StringUtils.isNotBlank(searchDto.getCategory())) {
						expressions.add(cb.equal(root.<EProductCagetory> get("category"),
								EnumHelper.translate(EProductCagetory.class, searchDto.getCategory())));
					}
				}
				return predicate;
			}
		};
		Pageable pageRequest = PaginationUtil.buildPageable(searchDto);
		Page<ProductPo> pos = productRepository.findAll(specification, pageRequest);
		PageModelDto<ProductDto> result = PaginationUtil.packPage(pos, new Function<ProductPo, ProductDto>() {
			@Override
			public ProductDto apply(ProductPo po) {
				ProductDto dto = ConverterService.convert(po, ProductDto.class);
				return dto;
			}
		});
		return result;
	}
}
