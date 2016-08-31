package com.lcw.herakles.platform.common.paging;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.dto.page.PageModelDto;
import com.lcw.herakles.platform.common.dto.page.PageModelReqDto;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.exception.BizServiceException;

/**
 * @author chenwulou
 *
 */
public class BasePageUtil {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(PaginationUtil.class);

	private static Pageable buildPageable(final PageModelReqDto requestDto) {
		Sort sort = null;
		if (requestDto.getPageSize() > 0) {
			return new PageRequest(requestDto.getPageNo(), requestDto.getPageSize(), sort);
		} else {
			throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "length should be greater than 0");
		}
	}

	public static Pageable buildPageable(final PageModelReqDto requestDto, String defaultDescOrderFieldName) {
		Pageable pageable = buildPageable(requestDto);
		if (null == pageable.getSort()) {
			if (StringUtils.isNotBlank(defaultDescOrderFieldName)) {
				Sort sort = new Sort(Direction.DESC, defaultDescOrderFieldName);
				pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			}
		}
		return pageable;
	}

	public static Pageable buildPageable(final PageModelReqDto requestDto, Direction direction,
			String... orderFieldNames) {
		Pageable pageable = buildPageable(requestDto);
		if (null == pageable.getSort()) {
			if (orderFieldNames != null && orderFieldNames.length > 0) {
				Sort sort = new Sort(direction, orderFieldNames);
				pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			}
		}
		return pageable;
	}

	public static Pageable buildPageable(final PageModelReqDto requestDto, List<Order> orders) {
		Pageable pageable = buildPageable(requestDto);
		if (null == pageable.getSort()) {
			if (orders != null && !orders.isEmpty()) {
				Sort sort = new Sort(orders);
				pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			}
		}
		return pageable;
	}

	public static Pageable buildPageable(final PageModelReqDto requestDto, Order... orders) {
		Pageable pageable = buildPageable(requestDto);
		if (null == pageable.getSort()) {
			if (orders != null && orders.length > 0) {
				Sort sort = new Sort(orders);
				pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			}
		}
		return pageable;
	}

	public static <F, T> PageModelDto<T> packPage(Page<F> page, Function<F, T> poConverter) {
		PageModelDto<T> result = new PageModelDto<T>();
		List<T> data = Lists.newArrayList();
		for (F f : page.getContent()) {
			T t = poConverter.apply(f);
			data.add(t);
		}
		result.setData(data);
		result.setPageNo(page.getNumber());
		result.setPageSize(page.getSize());
		result.setTotalRecords(page.getTotalElements());
		return result;
	}

}
