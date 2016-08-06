package com.lcw.herakles.platform.system.security.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.system.user.entity.UserPo;
import com.lcw.herakles.platform.system.user.enumeration.EEductation;
import com.lcw.herakles.platform.system.user.enumeration.EIdType;
import com.lcw.herakles.platform.system.user.enumeration.EJobType;
import com.lcw.herakles.platform.system.user.enumeration.ESex;
import com.lcw.herakles.platform.system.user.enumeration.converter.EEductationConverter;
import com.lcw.herakles.platform.system.user.enumeration.converter.EIdTypeConverter;
import com.lcw.herakles.platform.system.user.enumeration.converter.EJobTypeConverter;
import com.lcw.herakles.platform.system.user.enumeration.converter.ESexConverter;

/**
 * @author chenwulou
 *
 */
@Entity
@Table(name = "SYS_PRSNL_EXT")
@EntityListeners(IdInjectionEntityListener.class)
public class PersonalInfoPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "SNAME")
	private String sname;

	@Column(name = "BIRTH_DT")
	private String birthday;

	@Convert(converter = ESexConverter.class)
	@Column(name = "SEX")
	private ESex sex;

	@Convert(converter = EIdTypeConverter.class)
	@Column(name = "ID_TYPE")
	private EIdType idType;

	@Column(name = "ID_NO")
	private String idNo;

	@Column(name = "ID_IMG_1")
	private String idImage1;

	@Column(name = "ID_IMG_2")
	private String idImage2;

	@Convert(converter = EJobTypeConverter.class)
	@Column(name = "JOB_TYPE")
	private EJobType jobType;

	@Column(name = "JOB_DESC")
	private String jobDescription;

	@Convert(converter = EEductationConverter.class)
	@Column(name = "EDU_TYPE")
	private EEductation education;

	@Column(name = "TEL")
	private String telephone;

	@Column(name = "QQ")
	private String qq;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	@Column(name = "ADDR")
	private String address;

	@Column(name = "WORK_UNIT")
	private String workUnit;

	@OneToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private UserPo userPo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public ESex getSex() {
		return sex;
	}

	public void setSex(ESex sex) {
		this.sex = sex;
	}

	public EIdType getIdType() {
		return idType;
	}

	public void setIdType(EIdType idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdImage1() {
		return idImage1;
	}

	public void setIdImage1(String idImage1) {
		this.idImage1 = idImage1;
	}

	public String getIdImage2() {
		return idImage2;
	}

	public void setIdImage2(String idImage2) {
		this.idImage2 = idImage2;
	}

	public EJobType getJobType() {
		return jobType;
	}

	public void setJobType(EJobType jobType) {
		this.jobType = jobType;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public EEductation getEducation() {
		return education;
	}

	public void setEducation(EEductation education) {
		this.education = education;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public UserPo getUserPo() {
		return userPo;
	}

	public void setUserPo(UserPo userPo) {
		this.userPo = userPo;
	}

}
