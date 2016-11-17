package com.lcw.herakles.platform.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;

/**
 * Base Po
 * 
 * @author chenwulou
 *
 */
@MappedSuperclass
public class BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CREATE_OPID", insertable = true, updatable = false)
    @ColumnMeta(length = "40", nullable = false, comment = "创建人", order = 1000)
    private String createOpId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TS", insertable = true, updatable = false)
    @ColumnMeta(nullable = false, comment = "创建时间", order = 1001)
    private Date createTs;

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

}
