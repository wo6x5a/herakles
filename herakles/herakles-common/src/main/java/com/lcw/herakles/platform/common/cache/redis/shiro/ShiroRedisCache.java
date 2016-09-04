package com.lcw.herakles.platform.common.cache.redis.shiro;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.util.ApplicationContextUtil;

/**
 * redis for shiro cache 
 * 
 * @author chenwulou
 *
 * @param <K>
 * @param <V>
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private final String name;

    private RedisTemplate<K, V> keyValueCache;

    private RedisTemplate<String, K> nameKeysCache;

    public ShiroRedisCache(String name) {
        super();
        this.name = name;
    }

    @Override
    public V get(K key) throws CacheException {
        initTemplate();
        V value = (V) keyValueCache.opsForValue().get(key);
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value) throws CacheException {
        initTemplate();
        nameKeysCache.opsForSet().add(name, key);
        keyValueCache.opsForValue().set(key, value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        initTemplate();
        V value = keyValueCache.opsForValue().get(key);
        keyValueCache.opsForValue().getOperations().delete(key);
        nameKeysCache.opsForSet().remove(name, key);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        initTemplate();
        keyValueCache.opsForValue().getOperations().delete(keys());
        nameKeysCache.opsForSet().remove(name, keys());
    }

    @Override
    public int size() {
        initTemplate();
        Long size = nameKeysCache.opsForSet().size(name);
        return size.intValue();
    }

    @Override
    public Set<K> keys() {
        initTemplate();
        SetOperations<String, K> setOps = nameKeysCache.opsForSet();
        return setOps.members(name);
    }

    @Override
    public Collection<V> values() {
        List<V> values = Lists.newArrayList();
        for (K key : keys()) {
            values.add(get(key));
        }
        return values;
    }

    @SuppressWarnings("unchecked")
    private void initTemplate() {
        if (null == nameKeysCache || null == keyValueCache) {
            nameKeysCache = ApplicationContextUtil.getBean(RedisTemplate.class);
            keyValueCache = ApplicationContextUtil.getBean(RedisTemplate.class);
        }
    }

}
