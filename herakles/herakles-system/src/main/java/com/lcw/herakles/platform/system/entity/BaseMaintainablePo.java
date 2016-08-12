
package com.lcw.herakles.platform.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author chenwulou
 *
 */
@MappedSuperclass
public class BaseMaintainablePo extends BasePo {

	private static final long serialVersionUID = 1L;

	@Column(name = "LAST_MNT_OPID")
	private String lastMntOpId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MNT_TS")
	private Date lastMntTs;

	@Version
	@Column(name = "VERSION_CT")
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
