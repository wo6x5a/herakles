package com.lcw.herakles.platform.product.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.product.enums.EProductCagetory;
import com.lcw.herakles.platform.product.enums.converter.EProductCagetoryConverter;

/**
 * Class Name: Product Description: TODO
 * 
 * @author chenwulou
 * 
 */
@Entity
@Table(name = "PRODUCT")
@EntityListeners(IdInjectionEntityListener.class)
public class ProductPo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
    @ColumnMeta(length = "32", nullable = false, comment = "编号")
	private String id;

	@Column(name = "CATEGORY")
	@Convert(converter = EProductCagetoryConverter.class)
    @ColumnMeta(length = "3", nullable = false, comment = "类型")
	private EProductCagetory category;

	@Column(name = "NAME")
    @ColumnMeta(length = "80", nullable = false, comment = "名称")
	private String name;

	@Column(name = "DESCRIPTION")
    @ColumnMeta(length = "255", nullable = false, comment = "备注")
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (!this.getClass().isInstance(obj)) {
			return false;
		}
		return new EqualsBuilder().append(id, ((ProductPo) obj).getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}
}
