package com.lcw.herakles.platform.demo.dto.req;

import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * Class Name: ProductSearchDto Description: TODO
 * 
 * @author chenwulou
 *
 */
@ApiModel(value = "产品查询DTO")
public class ProductSearchDto extends DataTablesRequestDto {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字
     */
	@ApiModelProperty(value = "关键字")
    private String keyword;

	@ApiModelProperty(value = "产品类型")
    private String category;

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
