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

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@Entity
@Table(name = "sys_prsnl_ext")
@EntityListeners(IdInjectionEntityListener.class)
public class PersonalInfoPo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @ColumnMeta(length = "32", nullable = false, comment = "用户编号")
    private String userId;

    @Column(name = "sname")
    @ColumnMeta(length = "60", comment = "用户姓名")
    private String sname;

    @Column(name = "birth_dt")
    @ColumnMeta(length = "10", comment = "出生日期")
    private String birthday;

    @Convert(converter = ESexConverter.class)
    @Column(name = "sex")
    @ColumnMeta(length = "1", comment = "性别")
    private ESex sex;

    @Convert(converter = EIdTypeConverter.class)
    @Column(name = "id_type")
    @ColumnMeta(length = "2", comment = "证件类型")
    private EIdType idType;

    @Column(name = "id_no")
    @ColumnMeta(length = "100", uniqueKey = "ID_NO", comment = "证件号码")
    private String idNo;

    @Column(name = "id_img_1")
    @ColumnMeta(length = "100", comment = "证件正面图片")
    private String idImage1;

    @Column(name = "id_img_2")
    @ColumnMeta(length = "100", comment = "证件反面图片")
    private String idImage2;

    @Column(name = "id_start_dt")
    @ColumnMeta(length = "10", comment = "身份证有效期开始日期yyyy-MM-dd")
    private String personIdCardStartDate;

    @Column(name = "id_end_dt")
    @ColumnMeta(length = "10", comment = "身份证有效期截止日期yyyy-MM-dd")
    private String personIdCardEndDate;

    @Convert(converter = EJobTypeConverter.class)
    @Column(name = "job_type")
    @ColumnMeta(length = "3", comment = "工作类型")
    private EJobType jobType;

    @ColumnMeta(length = "100", comment = "工作简介")
    @Column(name = "job_desc")
    private String jobDescription;

    @Convert(converter = EEductationConverter.class)
    @Column(name = "edu_type")
    @ColumnMeta(length = "3", comment = "教育程度")
    private EEductation education;

    @Column(name = "tel")
    @ColumnMeta(length = "20", comment = "联系电话")
    private String telephone;

    @Column(name = "qq")
    @ColumnMeta(length = "20", comment = "QQ号码")
    private String qq;

    @Column(name = "wx")
    @ColumnMeta(length = "40", comment = "微信号码")
    private String wx;

    @Column(name = "fax")
    @ColumnMeta(length = "20", comment = "传真号码")
    private String fax;

    @Column(name = "zip_code")
    @ColumnMeta(length = "20", comment = "邮政编码")
    private String zipCode;

    @Column(name = "addr")
    @ColumnMeta(length = "200", comment = "联系地址")
    private String address;

    @Column(name = "work_unit")
    @ColumnMeta(length = "100", comment = "工作单位")
    private String workUnit;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserPo userPo;

}
