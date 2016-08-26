package com.lcw.herakles.platform.common.cache.redis.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class ShiroRedisCacheManager implements CacheManager {

    @SuppressWarnings("rawtypes")
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache c = cacheMap.get(name);
        if (c == null) {
            c = new ShiroRedisCache<K, V>(name);
            cacheMap.put(name, c);
        }
        return c;
    }
}
