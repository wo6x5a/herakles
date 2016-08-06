package com.lcw.herakles.platform.log.enumeration;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enumeration.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;
import com.lcw.herakles.platform.demo.entity.ProductPo;

/**
 * 实体类
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = PageEnumSerializer.class)
public enum EEntityType implements PageEnum {

	ALL("", "全部"), 
	PRODUCT(ProductPo.class.getSimpleName(), "产品类");

	EEntityType(String code, String text) {
		this.code = code;
		this.text = text;
	}

	private String code;

	private String text;

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}
}
