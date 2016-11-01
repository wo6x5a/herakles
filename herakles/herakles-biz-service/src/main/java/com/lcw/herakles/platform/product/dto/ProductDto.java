package com.lcw.herakles.platform.product.dto;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.product.enums.EProductCagetory;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author chenwulou
 * 
 */
@Getter
@Setter
@Comment("产品Dto")
public class ProductDto extends BaseMaintainableDto {

    /**
     * 
     */
    private static final long serialVersionUID = -3623606677366145221L;

    @Comment("编号")
    private String id;

    @Comment("产品类型")
    private EProductCagetory category;

    @Comment("名称")
    private String name;

    @Comment("备注")
    private String description;

}
