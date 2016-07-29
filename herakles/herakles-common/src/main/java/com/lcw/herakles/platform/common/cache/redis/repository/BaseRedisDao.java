package com.lcw.herakles.platform.common.cache.redis.repository;

import org.springframework.stereotype.Repository;

import com.lcw.herakles.platform.common.cache.redis.dto.RedisCommonReqDto;

/**
 * @author chenwulou
 *
 */
@Repository
public class BaseRedisDao extends AbstractBaseRedisDao<String, Object> {

	public void addObj(RedisCommonReqDto dto) {
		redisTemplate.opsForHash().put(dto.getKey(), dto.getObjectKey(), dto);
	}

	public Object getObj(RedisCommonReqDto dto) {
		return redisTemplate.opsForHash().get(dto.getKey(), dto.getObjectKey());
	}

	public void deleteObj(RedisCommonReqDto dto) {
		redisTemplate.opsForHash().delete(dto.getKey(), dto.getObjectKey());
	}

	public void addStr(String userId, String columnName, Object val) {
		redisTemplate.opsForHash().put(userId, columnName, val);
	}

	public Object getStr(String userId, String columnName) {
		return redisTemplate.opsForHash().get(userId, columnName);
	}

	public void deleteStr(String userId, String columnName) {
		redisTemplate.opsForHash().delete(userId, columnName);
	}

}
