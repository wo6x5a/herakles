
package com.lcw.herakles.platform.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;

import lombok.Getter;
import lombok.Setter;

/**
 * Base Maintainable Po
 * 
 * @author chenwulou
 *
 */
@Getter
@Setter
@MappedSuperclass
public class BaseMaintainablePo extends BasePo {

	private static final long serialVersionUID = 1L;

	@Column(name = "last_mnt_opid")
    @ColumnMeta(length = "32", comment = "最后修改人", order = 1002)
	private String lastMntOpId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_mnt_ts")
    @ColumnMeta(comment = "最后修改时间", order = 1003)
	private Date lastMntTs;

	@Version
	@Column(name = "version_ct")
    @ColumnMeta(comment = "版本号", defaultValue = "0", order = 1004)
	private Long versionCt;
}
