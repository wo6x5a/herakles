package com.lcw.herakles.platform.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenwulou
 *
 */
public class BaseDto implements Serializable{

	private static final long serialVersionUID = 1L;

    private String createOpId;
    
    private Date createTs;

	/**
	 * @return the createOpId
	 */
	public String getCreateOpId() {
		return createOpId;
	}

	/**
	 * @param createOpId the createOpId to set
	 */
	public void setCreateOpId(String createOpId) {
		this.createOpId = createOpId;
	}

	/**
	 * @return the createTs
	 */
	public Date getCreateTs() {
		return createTs;
	}

	/**
	 * @param createTs the createTs to set
	 */
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
    
    
}
