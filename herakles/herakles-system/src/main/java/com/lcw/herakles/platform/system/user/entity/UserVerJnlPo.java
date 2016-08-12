package com.lcw.herakles.platform.system.user.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.system.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.system.entity.id.IdInjectionEntityListener;
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
	@Comment(value = "流水号")
	private String jnlNo;

	@Comment(value = "用户编号")
	@Column(name = "USER_ID")
	private String userId;

	@Comment(value = "验证码状态")
	@Column(name = "STATUS")
	@Convert(converter = EVerStatusEnumConverter.class)
	private EVerStatus status;

	@Comment(value = "手机号")
	@Column(name = "MOBILE")
	private String mobile;

	@Comment(value = "验证码")
	@Column(name = "VER_CODE")
	private String verCode;

	@Comment(value = "验证码失效时间戳")
	@Column(name = "VER_END_DTTM")
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
