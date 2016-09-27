package com.lcw.herakles.platform.demo.dto.req;

import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;


/**
 * Class Name: ProductSearchDto Description: TODO
 * 
 * @author chenwulou
 *
 */
public class ProductSearchDto extends DataTablesRequestDto {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字
     */
    private String keyword;

    private EProductCagetory category;

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

    public EProductCagetory getCategory() {
        return category;
    }

    public void setCategory(EProductCagetory category) {
        this.category = category;
    }


}
