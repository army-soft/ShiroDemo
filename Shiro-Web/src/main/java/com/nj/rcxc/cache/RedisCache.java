package com.nj.rcxc.cache;

import com.nj.rcxc.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Fred on 2018/6/24.
 */
@Component
public class RedisCache<K,V> implements Cache<K,V> {
    @Autowired
    private JedisUtil jedisUtil;

    private final String CACHE_PREFIX = "master-cache:";

    private byte[] getKey(K k){
        if(k instanceof String){
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }
    @Override
    public V get(K k) throws CacheException {
        System.out.println("从Redis中获取权限数据");
        //可以在这里添加一个本地的缓存应用，将数据存储在本地
        //当第一次访问没有数据时去访问Redis服务器，然后将数据存储在本地的缓存中，可以减少对Redis的访问压力
        byte[] value = jedisUtil.get(getKey(k));
        if(value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        //一般都需要配置超时的条件
        jedisUtil.expire(key,600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.del(key);
        if(value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        //一般不要清空，会将其它的数据也清除造成误删除
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
