package com.weii.admin.web.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: weics
 * @Date: Created in 23:40 2018/07/05
 * @Description:
 * @Modified By:
 */
public class RedisCache<K, V> implements Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private RedisTemplate<String, Object> redisTempate; // 要提供有Redis处理工具类

    public RedisCache(RedisTemplate<String, Object> redisTempate) {
        this.redisTempate = redisTempate;
    }
    @Override
    public V get(K key) throws CacheException {
        logger.info("### get() : K = " + key);
        return (V) this.redisTempate.opsForValue().get(this.getKey(key));
    }
    @Override
    public V put(K key, V value) throws CacheException {
        logger.info("### put() : K = " + key + "、V = " + value);
        this.redisTempate.opsForValue().set(this.getKey(key), value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.info("### remove() : K = " + key);
        V val = this.get(key);
        this.redisTempate.delete(this.getKey(key));
        return val;
    }

    @Override
    public void clear() throws CacheException {
        logger.info("### clear()");
        this.redisTempate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb(); // 清空数据库
                return true;
            }
        });
    }

    @Override
    public int size() {
        logger.info("### size()");
        return this.redisTempate.execute(new RedisCallback<Integer>() {
            @Override
            public Integer doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.keys(getKey((K) "*").getBytes()).size();
            }
        });
    }

    @Override
    public Set<K> keys() {
        logger.info("### keys()");
        return this.redisTempate.execute(new RedisCallback<Set<K>>() {
            @Override
            public Set<K> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Set<K> set = new HashSet<K>();
                Set<byte[]> keys = connection.keys(getKey((K) "*").getBytes());
                Iterator<byte[]> iter = keys.iterator();
                while (iter.hasNext()) {
                    set.add((K) iter.next());
                }
                return set;
            }
        });
    }

    @Override
    public Collection<V> values() {
        logger.info("### values()");
        return this.redisTempate.execute(new RedisCallback<Set<V>>() {
            @Override
            public Set<V> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                Set<V> set = new HashSet<V>();
                Set<byte[]> keys = connection.keys(getKey((K) "*").getBytes());
                Iterator<byte[]> iter = keys.iterator();
                while (iter.hasNext()) {
                    set.add((V) connection.get(iter.next()));
                }
                return set;
            }
        });
    }

    private String getKey(K key) {
        return key.toString();
    }
}