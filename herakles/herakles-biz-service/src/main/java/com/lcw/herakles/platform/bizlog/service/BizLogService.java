package com.lcw.herakles.platform.bizlog.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.lcw.herakles.platform.bizlog.dto.BizLogDto;
import com.lcw.herakles.platform.bizlog.dto.req.BizLogQueryDto;
import com.lcw.herakles.platform.bizlog.entity.BizLogPo;
import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.bizlog.reposipory.BizLogRepository;
import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.enums.DBStrEnum;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.paging.PaginationUtil;
import com.lcw.herakles.platform.common.service.BaseService;
import com.lcw.herakles.platform.common.util.ErrorUtil;
import com.lcw.herakles.platform.common.util.ReflectionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwulou
 *
 */
@Service
@Slf4j
public class BizLogService extends BaseService{

	@Autowired
	private BizLogRepository bizLogRepository;

	@Transactional(readOnly = true)
	public BizLogDto getLog(String logId) {
	    if(StringUtils.isBlank(logId)){
	        ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "logId不能为空");
	    }
		return ConverterService.convert(bizLogRepository.findOne(logId), BizLogDto.class);
	}

	/**
	 * 添加log
	 * 
	 * @param entity 操作实体
	 * @param optType 操作类型
	 * @param operate 具体操作
     * @param entityId 实体id
	 * @param optIp 操作IP地址
	 * @param oldObject 操作前实体对象
	 * @param newObject 操作后实体对象
	 * @param descr 备注
	 */
	@Deprecated
    @Transactional
    public void save(Class<?> entity, EOptType optType, String operate, String entityId,
            String optIp, Object oldObject, Object newObject, String descr) {
        String oldValue = getVlaueByObj(oldObject);
        String newVaule = getVlaueByObj(newObject);
        BizLogPo bizLogPo = new BizLogPo();
        bizLogPo.setEntity(entity.getSimpleName());
        bizLogPo.setEntityId(entityId);
        bizLogPo.setOptType(optType);
        bizLogPo.setOperate(operate);
        bizLogPo.setOptIp(optIp);
        bizLogPo.setOldVaule(oldValue);
        bizLogPo.setNewVaule(newVaule);
        bizLogPo.setDescr(descr);
        super.setCreateInfo(bizLogPo);
        bizLogRepository.save(bizLogPo);
    }

    /**
     * 添加log
     * 
     * @param entity 操作实体
     * @param optType 操作类型
     * @param operate 具体操作
     * @param entityId 实体id
     * @param optIp 操作IP地址
     * @param oldObject 操作前实体对象
     * @param newObject 操作后实体对象
     * @param descr 备注
     */
    @Transactional
    public void saveEditLog(Class<?> entity, EOptType optType, String operate, String entityId,
            String optIp, Object oldObject, Object newObject, String descr) {
        Map<String, String> oldValueMap = Maps.newHashMap();
        Map<String, String> newValueMap = Maps.newHashMap();
        List<Field> fields = ReflectionUtil.getFieldsIncludingSuperClasses(oldObject.getClass());
        for (Field field : fields) {
            if (isNotColumn(field))
                continue;
            try {
                field.setAccessible(true);
                Object oldValue = field.get(oldObject);
                Object newValue = field.get(newObject);
                if (notEqual(oldValue, newValue)) {
                    ColumnMeta columnMeta = AnnotationUtils.getAnnotation(field, ColumnMeta.class);
                    String columnText = columnMeta.comment();
                    String oldValueText = null;
                    String newValueText = null;
                    if (DBIntEnum.class.isAssignableFrom(field.getType())
                            || DBStrEnum.class.isAssignableFrom(field.getType())) {
                        oldValueText = getEnumText(oldValue, oldValueText);
                        newValueText = getEnumText(newValue, newValueText);
                    } else {
                        oldValueText = oldValue == null ? null : oldValue.toString();
                        newValueText = newValue == null ? null : newValue.toString();
                    }
                    oldValueMap.put(columnText, oldValueText);
                    newValueMap.put(columnText, newValueText);
                }
            } catch (Exception e) {
                log.error("error while getting property", e);
            }
        }
        BizLogPo bizLogPo = new BizLogPo();
        bizLogPo.setEntity(entity.getSimpleName());
        bizLogPo.setEntityId(entityId);
        bizLogPo.setOptType(optType);
        bizLogPo.setOperate(operate);
        bizLogPo.setOptIp(optIp);
        bizLogPo.setOldVaule(convertMap2String(oldValueMap));
        bizLogPo.setNewVaule(convertMap2String(newValueMap));
        bizLogPo.setDescr(descr);
        super.setCreateInfo(bizLogPo);
        bizLogRepository.save(bizLogPo);
    }

	@Transactional(readOnly = true)
	public DataTablesResponseDto<BizLogDto> searchLog(final BizLogQueryDto queryDto) {

		Specification<BizLogPo> specification = new Specification<BizLogPo>() {
			@Override
			public Predicate toPredicate(Root<BizLogPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (queryDto != null) {
					if (StringUtils.isNotBlank(queryDto.getId())) {
                        expressions.add(cb.equal(root.<String> get("id"), queryDto.getId()));
					}
					if (null != queryDto.getType() && EOptType.ALL != queryDto.getType()) {
						expressions.add(cb.equal(root.<EOptType> get("optType"), queryDto.getType()));
					}
                    if (StringUtils.isNotBlank(queryDto.getEntity())) {
                        expressions.add(cb.equal(cb.lower(root.<String> get("entity")), queryDto.getEntity()));
                    }
				}
				return predicate;
			}
		};
		Pageable pageRequest = PaginationUtil.buildPageRequest(queryDto);
		Page<BizLogPo> pos = bizLogRepository.findAll(specification, pageRequest);
		DataTablesResponseDto<BizLogDto> result = PaginationUtil.populateFromPage(pos, new Function<BizLogPo, BizLogDto>() {
			@Override
			public BizLogDto apply(BizLogPo po) {
				BizLogDto dto = ConverterService.convert(po, BizLogDto.class);
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

    private boolean isNotColumn(Field field) {
        ColumnMeta columnMeta = AnnotationUtils.getAnnotation(field, ColumnMeta.class);
        return null == columnMeta;
    }

    private boolean notEqual(Object oldValue, Object newValue) {
        boolean result = false;
        if (null == oldValue) {
            result = null != newValue;
        } else {
            result = !(oldValue.equals(newValue));
        }
        return result;
    }

    private String getEnumText(Object oldValue, String oldValueText) {
        if (null != oldValue) {
            try {
                oldValueText = ((DBIntEnum) oldValue).getText();
            } catch (Exception e) {
                oldValueText = ((DBStrEnum) oldValue).getText();
            }
        }
        return oldValueText;
    }

	private String convertMap2String(Map<String, String> value) {
		String result = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			result = objectMapper.writeValueAsString(value);
		} catch (IOException e) {
            log.error("LogService.convertMap2String error:", e);
        }
		return result;
	}
}
