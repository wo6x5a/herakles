package com.lcw.herakles.platform.common.dto.page;

import java.io.Serializable;
import java.util.List;

import com.lcw.herakles.platform.common.annotation.Comment;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装分页信息
 * 
 * @author chenwulou
 *
 * @param <E>
 */
@Getter
@Setter
@Comment(value = "封装分页信息")
public class PageModelDto<E> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Comment(value = "结果集 ")
    private List<E> data;

    @Comment(value = "查询记录数 ")
    private long totalRecords;

    @Comment(value = "第几页 ")
    private long pageNo;

    @Comment(value = "每页多少条数据 ")
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

}
