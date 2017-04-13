package com.lcw.herakles.platform.system.config.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.lcw.herakles.platform.common.cache.util.CacheUtil;
import com.lcw.herakles.platform.common.converter.ConverterService;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.paging.PaginationUtil;
import com.lcw.herakles.platform.common.service.BaseService;
import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.common.util.ErrorUtil;
import com.lcw.herakles.platform.system.config.dto.ConfigDto;
import com.lcw.herakles.platform.system.config.dto.req.CfgModReqDto;
import com.lcw.herakles.platform.system.config.dto.req.CfgSearchDto;
import com.lcw.herakles.platform.system.config.entity.ConfigPo;
import com.lcw.herakles.platform.system.config.enums.ECfgType;
import com.lcw.herakles.platform.system.config.enums.EConfig;
import com.lcw.herakles.platform.system.config.repository.ConfigRepository;

/**
 * Class Name: ConfigService Description: TODO
 * 
 * @author chenwulou
 *
 */
@Service
public class ConfigService extends BaseService {

    @Autowired
    private ConfigRepository configRepository;

    @Transactional(readOnly = true)
    public DataTablesResponseDto<ConfigDto> search(final CfgSearchDto searchDto,
            final List<ECfgType> cfgTypeList) {
        Specification<ConfigPo> spec = new Specification<ConfigPo>() {
            @Override
            public Predicate toPredicate(Root<ConfigPo> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                expressions.add(root.<ECfgType>get("type").in(cfgTypeList));
                String keyWord = StringUtils.deleteWhitespace(searchDto.getKeyword());
                if (StringUtils.isNotBlank(keyWord)) {
                    expressions.add(cb.like(root.<String>get("memo"), "%" + keyWord + "%"));
                }
                return predicate;
            }
        };
        Pageable pageable = PaginationUtil.buildPageable(searchDto, "lastMntTs");
        Page<ConfigPo> configPos = configRepository.findAll(spec, pageable);
        DataTablesResponseDto<ConfigDto> result =
                PaginationUtil.populateFromPage(configPos, new Function<ConfigPo, ConfigDto>() {
                    @Override
                    public ConfigDto apply(ConfigPo po) {
                        ConfigDto dto = ConverterService.convert(po, ConfigDto.class);
                        dto.setKey(EnumHelper.translate(EConfig.class, po.getKey()));
                        return dto;
                    }
                }, searchDto.getEcho());
        return result;
    }

    @CachePut(value = CacheUtil.CACHE_CONFIG, key = "'CONFIG_' + #reqDto.key")
    @Transactional
    public void modify(CfgModReqDto reqDto) {
        ConfigPo cfg = configRepository.findOne(reqDto.getKey());
        if (cfg == null) {
            ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "参数不存在");
        }
        if (cfg.getType() != ECfgType.BIZ) {
            ErrorUtil.throwBizException(EErrorCode.COMM_ERROR_HINTS, "此类型参数不允许修改");
        }
        cfg.setValue(reqDto.getValue());
        cfg.setDescr(reqDto.getDescr());
        super.setUpdateInfo(cfg);
        configRepository.save(cfg);
    }

    @Cacheable(value = CacheUtil.CACHE_CONFIG, key = "'CONFIG_' + #key")
    @Transactional(readOnly = true)
    public ConfigDto findByKey(String key) {
        return ConverterService.convert(configRepository.findOne(key), ConfigDto.class);
    }

    /**
     * 清空CONFIG_CACHE所有缓存
     */
    @CacheEvict(value = CacheUtil.CACHE_CONFIG, allEntries = true)
    public void refresh() {}

}
