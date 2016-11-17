package com.lcw.herakles.platform.system.user.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.system.user.enums.EEductation;
import com.lcw.herakles.platform.system.user.enums.EIdType;
import com.lcw.herakles.platform.system.user.enums.EJobType;
import com.lcw.herakles.platform.system.user.enums.ESex;
import com.lcw.herakles.platform.system.user.enums.converter.EEductationConverter;
import com.lcw.herakles.platform.system.user.enums.converter.EIdTypeConverter;
import com.lcw.herakles.platform.system.user.enums.converter.EJobTypeConverter;
import com.lcw.herakles.platform.system.user.enums.converter.ESexConverter;

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
    @ColumnMeta(length = "32", nullable = false, comment = "用户编号")
    private String userId;

    @Column(name = "SNAME")
    @ColumnMeta(length = "60", comment = "用户姓名")
    private String sname;

    @Column(name = "BIRTH_DT")
    @ColumnMeta(length = "10", comment = "出生日期")
    private String birthday;

    @Convert(converter = ESexConverter.class)
    @Column(name = "SEX")
    @ColumnMeta(length = "10", comment = "性别")
    private ESex sex;

    @Convert(converter = EIdTypeConverter.class)
    @Column(name = "ID_TYPE")
    @ColumnMeta(length = "10", comment = "证件类型")
    private EIdType idType;

    @Column(name = "ID_NO")
    @ColumnMeta(length = "100", uniqueKey = "ID_NO", comment = "证件号码")
    private String idNo;

    @Column(name = "ID_IMG_1")
    @ColumnMeta(length = "100", comment = "证件正面图片")
    private String idImage1;

    @Column(name = "ID_IMG_2")
    @ColumnMeta(length = "100", comment = "证件反面图片")
    private String idImage2;

    @Column(name = "ID_START_DT")
    @ColumnMeta(length = "10", comment = "身份证有效期开始日期yyyy-MM-dd")
    private String personIdCardStartDate;

    @Column(name = "ID_END_DT")
    @ColumnMeta(length = "10", comment = "身份证有效期截止日期yyyy-MM-dd")
    private String personIdCardEndDate;

    @Convert(converter = EJobTypeConverter.class)
    @Column(name = "JOB_TYPE")
    @ColumnMeta(length = "10", comment = "工作类型")
    private EJobType jobType;

    @ColumnMeta(length = "100", comment = "工作简介")
    @Column(name = "JOB_DESC")
    private String jobDescription;

    @Convert(converter = EEductationConverter.class)
    @Column(name = "EDU_TYPE")
    @ColumnMeta(length = "10", comment = "教育程度")
    private EEductation education;

    @Column(name = "TEL")
    @ColumnMeta(length = "20", comment = "联系电话")
    private String telephone;

    @Column(name = "QQ")
    @ColumnMeta(length = "20", comment = "QQ号码")
    private String qq;

    @Column(name = "WX")
    @ColumnMeta(length = "40", comment = "微信号码")
    private String wx;

    @Column(name = "FAX")
    @ColumnMeta(length = "20", comment = "传真号码")
    private String fax;

    @Column(name = "ZIP_CODE")
    @ColumnMeta(length = "20", comment = "邮政编码")
    private String zipCode;

    @Column(name = "ADDR")
    @ColumnMeta(length = "200", comment = "联系地址")
    private String address;

    @Column(name = "WORK_UNIT")
    @ColumnMeta(length = "100", comment = "工作单位")
    private String workUnit;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
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
