package com.lcw.herakles.platform.system.log.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.paging.PaginationUtil;
import com.lcw.herakles.platform.system.log.dto.LogDto;
import com.lcw.herakles.platform.system.log.dto.req.LogQueryDto;
import com.lcw.herakles.platform.system.log.entity.LogPo;
import com.lcw.herakles.platform.system.log.enumeration.ELogType;
import com.lcw.herakles.platform.system.log.repository.LogRepository;

/**
 * @author chenwulou
 *
 */
@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;

	@Transactional(readOnly = true)
	public DataTablesResponseDto<LogDto> getDatatableLogs(final LogQueryDto queryDto) {

		Specification<LogPo> specification = new Specification<LogPo>() {
			@Override
			public Predicate toPredicate(Root<LogPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (queryDto != null) {
					if (StringUtils.isNotBlank(queryDto.getId())) {
						expressions.add(cb.like(cb.lower(root.<String> get("id")),
								"%" + queryDto.getId().trim().toLowerCase() + "%"));
					}
					if (null != queryDto.getType()) {
						expressions.add(cb.equal(root.<ELogType> get("category"), queryDto.getType()));
					}
				}
				return predicate;
			}
		};
		Pageable pageRequest = PaginationUtil.buildPageRequest(queryDto);
		Page<LogPo> pos = logRepository.findAll(specification, pageRequest);
		DataTablesResponseDto<LogDto> result = PaginationUtil.populateFromPage(pos,
				new Function<LogPo, LogDto>() {
					@Override
					public LogDto apply(LogPo po) {
						LogDto dto = ConverterService.convert(po, LogDto.class);
						return dto;
					}
				}, queryDto.getEcho());
		return result;
	}

	// @Transactional(readOnly = true)
	// public PageModelDto<LogDto> getPageLogs(final LogQueryDto queryDto) {
	//
	// Specification<LogPo> specification = new Specification<LogPo>() {
	// @Override
	// public Predicate toPredicate(Root<LogPo> root, CriteriaQuery<?> query,
	// CriteriaBuilder cb) {
	// Predicate predicate = cb.conjunction();
	// List<Expression<Boolean>> expressions = predicate.getExpressions();
	//
	// if (queryDto != null) {
	// if (StringUtils.isNotBlank(queryDto.getId())) {
	// expressions.add(cb.like(cb.lower(root.<String>get("id")),
	// "%" + queryDto.getId().trim().toLowerCase() + "%"));
	// }
	// if (null != queryDto.getType()) {
	// expressions
	// .add(cb.equal(root.<ELogType>get("category"), queryDto.getType()));
	// }
	// }
	// return predicate;
	// }
	// };
	// Pageable pageRequest = PaginationUtil.buildPageable(queryDto,
	// "createTs");
	// Page<LogPo> pos = logRepository.findAll(specification, pageRequest);
	// PageModelDto<LogDto> result = PaginationUtil.packPage(pos, new
	// Function<LogPo, LogDto>() {
	// @Override
	// public LogDto apply(LogPo po) {
	// LogDto dto = ConverterService.convert(po, LogDto.class);
	// return dto;
	// }
	// });
	// return result;
	// }
}
