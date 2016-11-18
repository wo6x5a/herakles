package com.lcw.herakles.platform.system.user.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.system.user.enums.EVerStatus;
import com.lcw.herakles.platform.system.user.enums.converter.EVerStatusEnumConverter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@Comment(value = "用户验证码表")
@Entity
@Table(name = "sys_user_ver_jnl")
@EntityListeners(IdInjectionEntityListener.class)
public class UserVerJnlPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "jnl_no")
    @ColumnMeta(length = "32", nullable = false, comment = "流水号")
	private String jnlNo;

	@Column(name = "user_id")
    @ColumnMeta(length = "32", nullable = false, comment = "用户编号")
	private String userId;

	@Column(name = "status")
	@Convert(converter = EVerStatusEnumConverter.class)
    @ColumnMeta(length = "1", nullable = false, comment = "验证码状态")
	private EVerStatus status;

	@Column(name = "mobile")
    @ColumnMeta(length = "20", nullable = false, comment = "手机号")
	private String mobile;

	@Column(name = "ver_code")
    @ColumnMeta(length = "100", nullable = false, comment = "验证码")
	private String verCode;

	@Column(name = "ver_end_dttm")
    @ColumnMeta(length = "14", nullable = false, comment = "验证码失效时间戳")
	private Long verEndDttm;

}
