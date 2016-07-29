package com.lcw.herakles.platform.demo.dto;

import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.demo.enumeration.EProductCagetory;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Class Name: ProductDto Description: TODO
 * 
 * @author chenwulou
 * 
 */
@ApiModel(value = "产品DTO")
public class ProductDto extends BaseMaintainableDto{

    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "产品编号")
    private String id;

	@ApiModelProperty(value = "产品类型")
    private EProductCagetory category;

	@ApiModelProperty(value = "产品名称")
    private String name;

	@ApiModelProperty(value = "备注")
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

    /**
     * Validation group for creating a product.
     */
    public interface CreateProduct {

    }

}
