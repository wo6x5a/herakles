package com.lcw.herakles.platform.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;

import lombok.Getter;
import lombok.Setter;

/**
 * Base Po
 * 
 * @author chenwulou
 *
 */
@Getter
@Setter
@MappedSuperclass
public class BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "create_opid", insertable = true, updatable = false)
    @ColumnMeta(length = "32", nullable = false, comment = "创建人", order = 1000)
    private String createOpId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_ts", insertable = true, updatable = false)
    @ColumnMeta(nullable = false, comment = "创建时间", order = 1001)
    private Date createTs;

}
