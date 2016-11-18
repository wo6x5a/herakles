
package com.lcw.herakles.platform.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;

/**
 * Base Maintainable Po
 * 
 * @author chenwulou
 *
 */
@MappedSuperclass
public class BaseMaintainablePo extends BasePo {

	private static final long serialVersionUID = 1L;

	@Column(name = "last_mnt_opid")
    @ColumnMeta(length = "40", comment = "最后修改人", order = 1002)
	private String lastMntOpId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_mnt_ts")
    @ColumnMeta(comment = "最后修改时间", order = 1003)
	private Date lastMntTs;

	@Version
	@Column(name = "version_ct")
    @ColumnMeta(comment = "版本号", defaultValue = "0", order = 1004)
	private Long versionCt;

	public String getLastMntOpId() {
		return lastMntOpId;
	}

	public void setLastMntOpId(String lastMntOpId) {
		this.lastMntOpId = lastMntOpId;
	}

	public Date getLastMntTs() {
		return lastMntTs;
	}

	public void setLastMntTs(Date lastMntTs) {
		this.lastMntTs = lastMntTs;
	}

	public Long getVersionCt() {
		return versionCt;
	}

	public void setVersionCt(Long versionCt) {
		this.versionCt = versionCt;
	}
}
