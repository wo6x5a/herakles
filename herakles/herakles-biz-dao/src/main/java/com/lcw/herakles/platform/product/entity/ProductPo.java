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

import lombok.Getter;
import lombok.Setter;

/**
 * Class Name: Product Description: TODO
 * 
 * @author chenwulou
 * 
 */
@Getter
@Setter
@Entity
@Table(name = "product")
@EntityListeners(IdInjectionEntityListener.class)
public class ProductPo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
    @ColumnMeta(length = "32", nullable = false, comment = "编号")
	private String id;

	@Column(name = "category")
	@Convert(converter = EProductCagetoryConverter.class)
    @ColumnMeta(length = "3", nullable = false, comment = "类型")
	private EProductCagetory category;

	@Column(name = "name")
    @ColumnMeta(length = "80", nullable = false, comment = "名称")
	private String name;

	@Column(name = "descr")
    @ColumnMeta(length = "255", nullable = false, comment = "备注")
	private String descr;

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
