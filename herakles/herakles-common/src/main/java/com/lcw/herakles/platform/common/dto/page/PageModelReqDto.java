package com.lcw.herakles.platform.common.dto.page;

import java.io.Serializable;

public class PageModelReqDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 第几页
	private int pageNo;

	// 每页多少条数据
	private int pageSize;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
