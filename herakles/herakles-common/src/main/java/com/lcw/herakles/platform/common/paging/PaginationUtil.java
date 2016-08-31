package com.lcw.herakles.platform.common.paging;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesResponseDto;

/**
 * @author chenwulou
 *
 */
public final class PaginationUtil extends BasePageUtil {

	public static Pageable buildPageRequest(DataTablesRequestDto requestDto) {
		Sort sort = null;
		List<Integer> sortedColumns = requestDto.getSortedColumns();// 列
		List<String> sortDirections = requestDto.getSortDirections();// 排序方式
		List<String> dataProps = requestDto.getDataProps();// 根据什么排序

		if (sortedColumns != null) {
			for (Integer item : sortedColumns) {
				String sortColumn = dataProps.get(item);
				int indexOf = 0;
				if (StringUtils.isNotBlank(sortColumn) && sortColumn.endsWith(".text")) {
					indexOf = sortColumn.lastIndexOf(".text");
				}
				if (indexOf > 0) {
					sortColumn = sortColumn.substring(0, indexOf);
				}
				String sortDir = sortDirections.get(0);
				sort = new Sort(Direction.fromString(sortDir), sortColumn);
			}
		}
		PageRequest page = new PageRequest(requestDto.getDisplayStart() / requestDto.getDisplayLength(),
				requestDto.getDisplayLength(), sort);
		return page;
	}

	public static Pageable buildPageable(final DataTablesRequestDto requestDto,
			String defaultDescOrderFieldName) {
		Pageable pageable = buildPageable(requestDto);
		if (null == pageable.getSort()) {
			if (StringUtils.isNotBlank(defaultDescOrderFieldName)) {
				Sort sort = new Sort(Direction.DESC, defaultDescOrderFieldName);
				pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			}
		}
		return pageable;
	}

	public static Pageable buildPageable(final DataTablesRequestDto requestDto, Direction direction,
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

	public static Pageable buildPageable(final DataTablesRequestDto requestDto, List<Order> orders) {
		Pageable pageable = buildPageable(requestDto);
		if (null == pageable.getSort()) {
			if (orders != null && !orders.isEmpty()) {
				Sort sort = new Sort(orders);
				pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			}
		}
		return pageable;
	}

	public static Pageable buildPageable(final DataTablesRequestDto requestDto, Order... orders) {
		Pageable pageable = buildPageable(requestDto);
		if (null == pageable.getSort()) {
			if (orders != null && orders.length > 0) {
				Sort sort = new Sort(orders);
				pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
			}
		}
		return pageable;
	}

	public static <F, T> DataTablesResponseDto<T> populateFromPage(Page<F> page, Function<F, T> poConverter, String echo) {
		DataTablesResponseDto<T> result = new DataTablesResponseDto<T>();
		List<T> data = Lists.newArrayList();
		result.setTotalDisplayRecords(page.getTotalElements());
		result.setTotalRecords(page.getTotalElements());
		for (F f : page.getContent()) {
			T t = poConverter.apply(f);
			data.add(t);
		}
		result.setData(data);
		result.setEcho(echo);
		return result;
	}
}
