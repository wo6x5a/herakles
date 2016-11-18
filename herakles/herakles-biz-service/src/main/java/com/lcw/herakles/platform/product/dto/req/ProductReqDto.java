package com.lcw.herakles.platform.product.dto.req;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.product.enums.EProductCagetory;
import com.lcw.herakles.platform.product.validation.NullCheck;
import com.lcw.herakles.platform.product.validation.ProductExistenceCheck;

import lombok.Getter;
import lombok.Setter;

/**
 * Class Name: ProductDto 
 * 
 * Description: TODO
 * 
 * @author chenwulou
 * 
 */
@Getter
@Setter
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
    private String descr;

    // TODO unuse
    private String file;

    /**
     * Validation group for creating a product.
     */
    public interface CreateProduct {

    }

}
