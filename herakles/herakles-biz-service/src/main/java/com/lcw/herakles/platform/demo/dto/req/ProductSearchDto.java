package com.lcw.herakles.platform.demo.dto.req;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;

import lombok.Getter;
import lombok.Setter;


/**
 * Class Name: ProductSearchDto Description: TODO
 * 
 * @author chenwulou
 *
 */
@Getter
@Setter
@Comment(value = "产品搜索dto")
public class ProductSearchDto extends DataTablesRequestDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1678249793566204288L;

    @Comment(value = "关键字")
    private String keyword;

    @Comment(value = "种类")
    private EProductCagetory category;

}
