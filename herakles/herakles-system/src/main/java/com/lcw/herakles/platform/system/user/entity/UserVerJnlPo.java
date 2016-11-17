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

/**
 * @author chenwulou
 *
 */
@Comment(value = "用户验证码表")
@Entity
@Table(name = "SYS_USER_VER_JNL")
@EntityListeners(IdInjectionEntityListener.class)
public class UserVerJnlPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JNL_NO")
    @ColumnMeta(length = "32", nullable = false, comment = "流水号")
	private String jnlNo;

	@Column(name = "USER_ID")
    @ColumnMeta(length = "32", nullable = false, comment = "用户编号")
	private String userId;

	@Column(name = "STATUS")
	@Convert(converter = EVerStatusEnumConverter.class)
    @ColumnMeta(length = "1", nullable = false, comment = "验证码状态")
	private EVerStatus status;

	@Column(name = "MOBILE")
    @ColumnMeta(length = "20", nullable = false, comment = "手机号")
	private String mobile;

	@Column(name = "VER_CODE")
    @ColumnMeta(length = "100", nullable = false, comment = "验证码")
	private String verCode;

	@Column(name = "VER_END_DTTM")
    @ColumnMeta(length = "14", nullable = false, comment = "验证码失效时间戳")
	private Long verEndDttm;

	public String getJnlNo() {
		return jnlNo;
	}

	public void setJnlNo(String jnlNo) {
		this.jnlNo = jnlNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}

	public Long getVerEndDttm() {
		return verEndDttm;
	}

	public void setVerEndDttm(Long verEndDttm) {
		this.verEndDttm = verEndDttm;
	}

	public EVerStatus getStatus() {
		return status;
	}

	public void setStatus(EVerStatus status) {
		this.status = status;
	}
}
