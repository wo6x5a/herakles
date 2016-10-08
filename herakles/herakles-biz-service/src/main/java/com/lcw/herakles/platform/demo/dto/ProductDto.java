package com.lcw.herakles.platform.demo.dto;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;

/**
 * Class Name: ProductDto Description: TODO
 * 
 * @author chenwulou
 * 
 */
@Comment("产品Dto")
public class ProductDto extends BaseMaintainableDto{

    private static final long serialVersionUID = 1L;

    @Comment("编号")
    private String id;

    @Comment("产品类型")
    private EProductCagetory category;

    @Comment("名称")
    private String name;

    @Comment("备注")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EProductCagetory getCategory() {
        return category;
    }

    public void setCategory(EProductCagetory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
