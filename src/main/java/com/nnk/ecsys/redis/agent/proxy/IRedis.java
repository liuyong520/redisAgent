package com.nnk.ecsys.redis.agent.proxy;

import com.nnk.ecsys.redis.agent.model.CommonJsonBase;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis代理接口,第一个参数固定为Jedis
 * 
 * @author 张浩 2015-1-7
 */
public interface IRedis {

	// =======================ALL=======================

	public Boolean exists(Jedis jedis, String key);

	public Long del(Jedis jedis, String key);

	public Long del(Jedis jedis, String... keys);

	public Long expire(Jedis jedis, String key, int seconds);

	public Long expireAt(Jedis jedis, String key, long unixTime);

	public Long ttl(Jedis jedis, String key);

	// =======================String=======================

	public String setString(Jedis jedis, String key, String value, int seconds);

	public String setString(Jedis jedis, String key, String value);

    public String setString(Jedis jedis, byte[] key, byte[] value);

	public Long setStrings(Jedis jedis, String... keysvalues);

	public Long appendString(Jedis jedis, String key, String value);

	public String getString(Jedis jedis, String key);

    public byte[] getString(Jedis jedis, byte[] key);

	public List<String> getStrings(Jedis jedis, String... keys);

	// =======================byte[]=======================

	public String setBytes(Jedis jedis, String key, byte[] value, int seconds);

	public String setBytes(Jedis jedis, String key, byte[] value);

	public Long appendBytes(Jedis jedis, String key, byte[] value);

	public byte[] getBytes(Jedis jedis, String key);

	public List<byte[]> getByteses(Jedis jedis, String... keys);

	// =======================Object=======================

	public String setObject(Jedis jedis, String key, Object obj, int seconds);

	public String setObject(Jedis jedis, String key, Object obj);

	public String setObject(Jedis jedis, String key, CommonJsonBase obj, int seconds);

	public String setObject(Jedis jedis, String key, CommonJsonBase obj);

	public <T> T getObject(Jedis jedis, String key, Class<T> clazz);

	// =======================Map=======================

	public String setMap(Jedis jedis, String key, Map<String, String> hash);

	public void resetMap(Jedis jedis, String key, Map<String, String> hash);

    public String setMap(Jedis jedis, byte[] key, Map<byte[], byte[]> hash);

    public void resetMap(Jedis jedis, byte[] key, Map<byte[], byte[]> hash);

	public Long setMapValByKey(Jedis jedis, String key, String mapKey, String value);

	public boolean hexists(Jedis jedis, String key, String mapKey);

    public Long setMapValByKey(Jedis jedis, byte[] key, byte[] mapKey, byte[] value);

    public List<byte[]> getMapVals(Jedis jedis, byte[] key);

	public Set<String> getMapKeys(Jedis jedis, String key);

	public List<String> getMapVals(Jedis jedis, String key);

	public Map<String, String> getMap(Jedis jedis, String key);

    public Map<byte[], byte[]> getMap(Jedis jedis, byte[] key);

	public String getMapValByKey(Jedis jedis, String key, String mapKey);

    public byte[] getMapValByKey(Jedis jedis, byte[] key, byte[] mapKey);

	public List<String> getMapValsByKeys(Jedis jedis, String key, String... mapKeys);

	public Long getMapSize(Jedis jedis, String key);

	public Long delMapValsByKeys(Jedis jedis, String key, String... mapKeys);

	public Boolean containsKey(Jedis jedis, String key, String mapKey);

	// =======================List=======================

	public Long setList(Jedis jedis, String key, String... values);

	public List<String> getList(Jedis jedis, String key);

	public Long getListSize(Jedis jedis, String key);

	public String delListEnd(Jedis jedis, String key);

	// =======================Set=======================

	public Long setSet(Jedis jedis, String key, String... members);

	public Set<String> getSet(Jedis jedis, String key);

	public Long getSetSize(Jedis jedis, String key);

	public Long delSetMembers(Jedis jedis, String key, String... members);

	public String spop(Jedis jedis, String key);

	public Long setnx(Jedis jedis, String key, String value);

	public String getSet(Jedis jedis, String key, String value);

    // =======================sorted set===================
    public Set<String> zrevrangeByScore(Jedis jedis, String key, double max, double min);

    public Set<byte[]> zrevrangeByScore(Jedis jedis, byte[] key, double max, double min);

    public Set<String> zrange(Jedis jedis, String key, long start, long end);

    public Set<byte[]> zrange(Jedis jedis, byte[] key, long start, long end);

    public Double zscore(Jedis jedis, String key, String member);

    public Set<byte[]> zrevrange(Jedis jedis, byte[] key, long start, long end);

	// =======================Number=======================

	public Long incrBy(Jedis jedis, String key, long value);

	public Double incrByFloat(Jedis jedis, String key, double value);

    public Long hincrBy(Jedis jedis, String key, String field, long value);

    public String brpoplpush(Jedis jedis, String source, String destination, int timeout);

    public String rpoplpush(Jedis jedis, String source, String destination);

    public byte[] rpoplpush(Jedis jedis, byte[] source, byte[] destination);

    public Long lrem(Jedis jedis, String key, long count, String value);

    public Long lpush(Jedis jedis, String key, String value);

    public Long rpush(Jedis jedis, String key, String value);

    public List<String> lrange(Jedis jedis, String key, long start, long end);

    public long llen(Jedis jedis, String key);

    public String lpop(Jedis jedis, String key);

    public Long lpush(Jedis jedis, byte[] key, byte[] value);

    public Long rpush(Jedis jedis, byte[] key, byte[] value);

    public byte[] lpop(Jedis jedis, byte[] key);

    public long zadd(Jedis jedis, String key, double score, String member);

    public long zadd(Jedis jedis, String key, Map<String, Double> scoreMembers);

    public long zrem(Jedis jedis, String key, String member);
}
