package com.silverhu.session.framework.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class RedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate redisTemplate;

    private long expire;

    private final String SHIRO_CACHE_PREFIX_ = "shiro_cache_prefix_";

    public RedisCache(RedisTemplate redisTemplate, long expire) {
        super();
        this.redisTemplate = redisTemplate;
        this.expire = expire;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("RedisCache clear");
        Set<String> keys = redisTemplate.keys(SHIRO_CACHE_PREFIX_ + "*");
        redisTemplate.delete(keys);
    }

    @Override
    public V get(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        log.debug("RedisCache get : {}", k);
        return (V) redisTemplate.opsForValue().get(getCacheKey(k));
    }

    @Override
    public Set<K> keys() {
        log.debug("RedisCache keys");
        Set<String> keys = redisTemplate.keys(SHIRO_CACHE_PREFIX_ + "*");
        Set<K> sets = new HashSet<>();
        for (String cacheKey : keys) {
            sets.add(getK(cacheKey));
        }
        return sets;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        if (k == null || v == null) {
            return null;
        }
        log.debug("RedisCache put : {} = {}", k, v);
        redisTemplate.opsForValue().set(getCacheKey(k), v, expire, TimeUnit.MILLISECONDS);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        V value = get(k);
        log.debug("RedisCache remove : {} = {}", k, value);
        redisTemplate.delete(getCacheKey(k));
        return value;
    }

    @Override
    public int size() {
        log.debug("RedisCache size");
        return redisTemplate.keys(SHIRO_CACHE_PREFIX_ + "*").size();
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            values.add(get(k));
        }
        return values;
    }

    private K getK(String cacheKey) {
        if (cacheKey.startsWith(SHIRO_CACHE_PREFIX_)) {
            return (K) cacheKey.substring(SHIRO_CACHE_PREFIX_.length());
        }
        return (K) cacheKey;
    }

    private String getCacheKey(K k) {
        return SHIRO_CACHE_PREFIX_ + k.toString();
    }
}
