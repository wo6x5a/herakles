package com.lcw.herakles.platform.common.entity.id;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * @author chenwulou
 *
 */
public final class IdUtil {

	  /**
	   * generate uuid String
	   * 
	   * @return
	   */
	  public static String produce() {
	    String uuid = UUID.randomUUID().toString();
	    return StringUtils.replace(uuid, "-", "");
	  }
}
