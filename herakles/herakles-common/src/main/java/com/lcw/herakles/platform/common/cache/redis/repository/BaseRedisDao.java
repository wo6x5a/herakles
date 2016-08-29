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
		redisTemplate.opsForHash().put(dto.getKey(), dto.getHashKey(), dto);
	}

	public Object getObj(RedisCommonReqDto dto) {
		return redisTemplate.opsForHash().get(dto.getKey(), dto.getHashKey());
	}

	public void deleteObj(RedisCommonReqDto dto) {
		redisTemplate.opsForHash().delete(dto.getKey(), dto.getHashKey());
	}

	public void addStr(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	public Object getStr(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	public void deleteStr(String key, String hashKey) {
		redisTemplate.opsForHash().delete(key, hashKey);
	}

}
