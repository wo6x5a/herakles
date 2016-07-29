package com.lcw.herakles.platform.common.cache.redis.dto;

/**
 * @author chenwulou
 *
 */
public abstract class RedisCommonReqDto {

	/**
	 * @description:一般取值userId
	 * @return
	 */
	public abstract String getKey();
	
	/**
	 * @description:一般用dto的名称来命名，全大写
	 * @return
	 */
	public String getObjectKey(){
		return getClass().getSimpleName().toUpperCase();
	}
	
}
