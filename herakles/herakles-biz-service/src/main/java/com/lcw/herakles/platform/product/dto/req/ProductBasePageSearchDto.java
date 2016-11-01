package com.lcw.herakles.platform.product.dto.req;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.page.PageModelReqDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Comment(value = "产品基础分页搜索Dto")
public class ProductBasePageSearchDto extends PageModelReqDto {

    /**
     * 
     */
    private static final long serialVersionUID = -5977132019387111655L;

    @Comment(value = "关键字")
    private String keyword;

    @Comment(value = "种类")
    private String category;

}
