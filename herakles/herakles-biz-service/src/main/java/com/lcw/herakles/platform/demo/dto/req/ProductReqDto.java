package com.lcw.herakles.platform.demo.dto.req;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;
import com.lcw.herakles.platform.demo.validation.NullCheck;
import com.lcw.herakles.platform.demo.validation.ProductExistenceCheck;

/**
 * Class Name: ProductDto 
 * 
 * Description: TODO
 * 
 * @author chenwulou
 * 
 */
@Comment("产品请求Dto")
public class ProductReqDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6009850829236911823L;

    @Comment("产品编号")
    private String id;

    @NullCheck
    @Comment("产品类型")
    private EProductCagetory category;

    @ProductExistenceCheck(groups = {CreateProduct.class})
    @NotBlank(message = "{error.required.field}")
    @Size(max = 100, message = "{error.maximum.length.exceeded}")
    @Comment("产品名称")
    private String name;

    @Size(max = 200, message = "{error.maximum.length.exceeded}")
    @Comment("产品描述")
    private String description;

    // TODO unuse
    private String file;

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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Validation group for creating a product.
     */
    public interface CreateProduct {

    }

}
