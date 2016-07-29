package com.lcw.herakles.platform.common.dto.page;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页信息
 * 
 * @author chenwulou
 *
 * @param <E>
 */
public class PageModelDto<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 结果集 **/
	private List<E> data;

	/** 查询记录数 **/
	private long totalRecords;

	/** 第几页 **/
	private long pageNo;

	/** 每页多少条数据 **/
	private int pageSize;

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public long getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}

	/**
	 * 取得首页
	 * 
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public long getPreviousPageNo() {
		if (pageNo <= 1) {
			return 1;
		}
		return pageNo - 1;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public long getNextPageNo() {
		if (pageNo >= getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNo + 1;
	}

	/**
	 * 取得尾页
	 * 
	 * @return
	 */
	public long getBottomPageNo() {
		return getTotalPages();
	}

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}
}