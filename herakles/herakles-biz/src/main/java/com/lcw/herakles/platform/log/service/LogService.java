package com.lcw.herakles.platform.log.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lcw.herakles.platform.common.util.ReflectionUtil;
import com.lcw.herakles.platform.log.dto.LogDto;
import com.lcw.herakles.platform.log.dto.req.LogQueryDto;
import com.lcw.herakles.platform.log.entity.LogPo;
import com.lcw.herakles.platform.log.enums.EOptType;
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
	public void save(Class<?> entity, EOptType optType, String operate, String optIp, Object oldObject,
			Object newObject, String comment) {
		String oldValue = getVlaueByObj(oldObject);
		String newVaule = getVlaueByObj(newObject);
		LogPo logPo = new LogPo();
		logPo.setEntity(entity.getSimpleName());
		logPo.setOptType(optType);
		logPo.setOperate(operate);
		logPo.setOptIp(optIp);
		logPo.setOldVaule(oldValue);
		logPo.setNewVaule(newVaule);
		logPo.setComment(comment);
		logRepository.save(logPo);
	}

	@Transactional(readOnly = true)
	public DataTablesResponseDto<LogDto> searchLog(final LogQueryDto queryDto) {

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
					if (null != queryDto.getType() && EOptType.ALL != queryDto.getType()) {
						expressions.add(cb.equal(root.<EOptType> get("optType"), queryDto.getType()));
					}
					if (StringUtils.isNotBlank(queryDto.getEntity())) {
						expressions.add(cb.like(cb.lower(root.<String> get("entity")),
								"%" + queryDto.getEntity().trim().toLowerCase() + "%"));
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
		if (null == obj) {
			return null;
		}
		Map<String, String> objMap = ReflectionUtil.getFieldMap(obj);
		return this.convertMap2String(objMap);
	}

	private String convertMap2String(Map<String, String> value) {
		String result = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			result = objectMapper.writeValueAsString(value);
		} catch (IOException e) {
            LOGGER.error("LogService.convertMap2String error, {}", e);
        }
		return result;
	}
}
