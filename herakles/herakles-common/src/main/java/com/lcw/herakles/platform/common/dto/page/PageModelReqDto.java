package com.lcw.herakles.platform.common.dto.page;

import java.io.Serializable;

import com.lcw.herakles.platform.common.annotation.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Comment(value = "分页模板请求dto")
public class PageModelReqDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Comment(value = "第几页")
    private int pageNo;

    @Comment(value = "每页多少条数据")
    private int pageSize;

}
