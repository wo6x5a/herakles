package com.lcw.herakles.platform.common.dto;

import java.util.Date;

/**
 * @author chenwulou
 *
 */
public class BaseMaintainableDto extends BaseDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String lastMntOpId;
    
    private Date lastMntTs;

	/**
	 * @return the lastMntOpId
	 */
	public String getLastMntOpId() {
		return lastMntOpId;
	}

	/**
	 * @param lastMntOpId the lastMntOpId to set
	 */
	public void setLastMntOpId(String lastMntOpId) {
		this.lastMntOpId = lastMntOpId;
	}

	/**
	 * @return the lastMntTs
	 */
	public Date getLastMntTs() {
		return lastMntTs;
	}

	/**
	 * @param lastMntTs the lastMntTs to set
	 */
	public void setLastMntTs(Date lastMntTs) {
		this.lastMntTs = lastMntTs;
	}
    
}
