package com.lcw.herakles.platform.demo.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;
import com.lcw.herakles.platform.demo.enums.converter.EProductCagetoryConverter;

/**
 * Class Name: Product Description: TODO
 * 
 * @author chenwulou
 * 
 */
@Entity
@Table(name = "product")
@EntityListeners(IdInjectionEntityListener.class)
public class ProductPo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "category")
	@Convert(converter = EProductCagetoryConverter.class)
	private EProductCagetory category;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
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
