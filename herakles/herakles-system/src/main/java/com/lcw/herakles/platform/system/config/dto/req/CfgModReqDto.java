package com.lcw.herakles.platform.system.config.dto.req;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * 参数修改dto
 * 
 * @author chenwulou
 *
 */
@Getter
@Setter
public class CfgModReqDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "{common.error.field.empty}")
	private String key;
	
	@NotEmpty(message = "{common.error.field.empty}")
	private String trxPwd;

	private String value;
	
	private String descr;
}
