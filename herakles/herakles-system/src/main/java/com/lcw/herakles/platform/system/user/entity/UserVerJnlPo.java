package com.lcw.herakles.platform.system.user.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.system.user.enumeration.EVerStatus;
import com.lcw.herakles.platform.system.user.enumeration.converter.EVerStatusEnumConverter;

/**
 * @author chenwulou
 *
 */
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
    private String jnlNo;
	
    @Column(name = "USER_ID")
    private String userId;
	
    @Column(name = "STATUS")
	@Convert(converter = EVerStatusEnumConverter.class)
	private EVerStatus status;

    @Column(name = "MOBILE")
    private String mobile;
	
	@Column(name = "VER_CODE")
	private String verCode;
	
	@Column(name = "VER_END_DTTM")
	private String verEndDttm;

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

	public String getVerEndDttm() {
		return verEndDttm;
	}

	public void setVerEndDttm(String verEndDttm) {
		this.verEndDttm = verEndDttm;
	}

	public EVerStatus getStatus() {
		return status;
	}

	public void setStatus(EVerStatus status) {
		this.status = status;
	}
}
