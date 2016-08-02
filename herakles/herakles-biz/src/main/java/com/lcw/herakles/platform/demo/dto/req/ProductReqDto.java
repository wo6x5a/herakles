package com.lcw.herakles.platform.demo.dto.req;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.lcw.herakles.platform.demo.enumeration.EProductCagetory;
import com.lcw.herakles.platform.demo.validation.NullCheck;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Class Name: ProductDto Description: TODO
 * 
 * @author chenwulou
 * 
 */
@ApiModel(value = "产品请求DTO")
public class ProductReqDto implements Serializable {

	private static final long serialVersionUID = 1L;

	// @NotBlank(message = "{error.required.field}")
	// @ProductExistenceCheck(groups = { CreateProduct.class })
	@ApiModelProperty(value = "产品编号")
	private String id;

	@NullCheck
	@ApiModelProperty(value = "产品类型")
	private EProductCagetory category;

	@NotBlank(message = "{error.required.field}")
	@Size(max = 100, message = "{error.maximum.length.exceeded}")
	@ApiModelProperty(value = "产品名称")
	private String name;

	@Size(max = 200, message = "{error.maximum.length.exceeded}")
	@ApiModelProperty(value = "备注")
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
