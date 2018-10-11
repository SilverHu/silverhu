package com.silverhu.session.framework.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class RedisCacheManager implements CacheManager {

    private RedisTemplate redisTemplate;

    private long expire;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("ShiroRedisCacheManager getCache, name : {}", name);
        return new RedisCache<>(redisTemplate, expire);
    }
}
