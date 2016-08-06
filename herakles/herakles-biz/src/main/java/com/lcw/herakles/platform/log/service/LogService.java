package com.lcw.herakles.platform.log.service;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.paging.PaginationUtil;
import com.lcw.herakles.platform.common.util.ReflectionUtil;
import com.lcw.herakles.platform.log.dto.LogDto;
import com.lcw.herakles.platform.log.dto.req.LogQueryDto;
import com.lcw.herakles.platform.log.entity.LogPo;
import com.lcw.herakles.platform.log.enumeration.EEntityType;
import com.lcw.herakles.platform.log.enumeration.EOptType;
import com.lcw.herakles.platform.log.reposipory.LogRepository;

/**
 * @author chenwulou
 *
 */
@Service
public class LogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

	@Autowired
	private LogRepository logRepository;

	@Transactional(readOnly = true)
	public LogDto getLog(String logId) {
		return ConverterService.convert(logRepository.findOne(logId), LogDto.class);
	}

	@Transactional
	public void save(EEntityType entity, EOptType optType, String operate, String optIp, Object oldObject,
			Object newObject, String comment) {
		String oldValue = getVlaueByObj(oldObject);
		String newVaule = getVlaueByObj(newObject);
		LogPo logPo = new LogPo();
		logPo.setEntity(entity);
		logPo.setOptType(optType);
		logPo.setOperate(operate);
		logPo.setOptIp(optIp);
		logPo.setOldVaule(oldValue);
		logPo.setNewVaule(newVaule);
		logPo.setComment(comment);
		logRepository.save(logPo);
	}

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
					if (null != queryDto.getType() && EEntityType.ALL != queryDto.getType()) {
						expressions.add(cb.equal(root.<EEntityType> get("category"), queryDto.getType()));
					}
				}
				return predicate;
			}
		};
		Pageable pageRequest = PaginationUtil.buildPageRequest(queryDto);
		Page<LogPo> pos = logRepository.findAll(specification, pageRequest);
		DataTablesResponseDto<LogDto> result = PaginationUtil.populateFromPage(pos, new Function<LogPo, LogDto>() {
			@Override
			public LogDto apply(LogPo po) {
				LogDto dto = ConverterService.convert(po, LogDto.class);
				return dto;
			}
		}, queryDto.getEcho());
		return result;
	}

	private String getVlaueByObj(Object obj) {
		Map<String, String> objMap = ReflectionUtil.getFieldMap(obj);
		return this.convertMap2String(objMap);

	}

	private String convertMap2String(Map<String, String> value) {
		String result = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			result = objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			LOGGER.error("LogService.convertMap2String error, {}", e);
		}
		return result;
	}
}
