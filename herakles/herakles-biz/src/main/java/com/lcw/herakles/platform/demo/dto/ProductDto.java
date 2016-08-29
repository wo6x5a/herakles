package com.lcw.herakles.platform.demo.dto;

import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;

/**
 * Class Name: ProductDto Description: TODO
 * 
 * @author chenwulou
 * 
 */
public class ProductDto extends BaseMaintainableDto{

    private static final long serialVersionUID = 1L;

    private String id;

    private EProductCagetory category;

    private String name;

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
