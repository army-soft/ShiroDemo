package com.nj.rcxc.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Fred on 2018/6/24.
 */
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisCache redisCache;
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
