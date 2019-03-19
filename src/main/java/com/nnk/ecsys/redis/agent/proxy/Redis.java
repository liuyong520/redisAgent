package com.nnk.ecsys.redis.agent.proxy;

import com.alibaba.fastjson.JSON;
import com.nnk.ecsys.redis.agent.model.CommonJsonBase;
import com.nnk.ecsys.redis.agent.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * redis委托类
 * 
 * @author 张浩 2015-1-7
 */
public class Redis implements IRedis {
	private static final Logger logger = LoggerFactory.getLogger(Redis.class);
	private static final Queue<String> twemproxys;
	private static final JedisPoolConfig poolConfig;
	private static final int DEFAULT_MAX_TOTAL = 10;
	private static final int DEFAULT_MAX_IDLE = 5;
	private static final int DEFAULT_MIN_IDLE = 1;
	private static final long DEFAULT_MAX_WAITMILLIS = 30000;
	private static final String REDIS_PROPERTIES = "classpath*:properties/redis.properties";
	private static final String DeafaulPathPrefix = "file:";
	private static JedisPool pool;
	static {
		Properties props = null;
		try {
			Resource resource = null;
			try{
				resource = ResourceUtils.getLastResource(REDIS_PROPERTIES);
			}catch (Exception e){

			}
			if(resource == null){
				String filePath = null;
				if(REDIS_PROPERTIES.contains(":")){
					String path = REDIS_PROPERTIES.substring(REDIS_PROPERTIES.indexOf(":")+1,REDIS_PROPERTIES.length());
					filePath = DeafaulPathPrefix + path;
				}else{
					filePath = DeafaulPathPrefix + REDIS_PROPERTIES;
				}
				logger.info("=============fileFath:" + filePath);
				resource = ResourceUtils.getLastResource(filePath);
			}
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		String addresses = props.getProperty("redis.twemproxy.addresses");
		twemproxys = new LinkedList<String>(Arrays.asList(addresses.split(",")));

		poolConfig = new JedisPoolConfig();
		if (props.containsKey("redis.poolConfig.max.total"))
			poolConfig.setMaxTotal(Integer.parseInt(props.getProperty("redis.poolConfig.max.total")));
		else
			poolConfig.setMaxTotal(DEFAULT_MAX_TOTAL);

		if (props.containsKey("redis.poolConfig.max.idle"))
			poolConfig.setMaxIdle(Integer.parseInt(props.getProperty("redis.poolConfig.max.idle")));
		else
			poolConfig.setMaxIdle(DEFAULT_MAX_IDLE);

		if (props.containsKey("redis.poolConfig.min.idle"))
			poolConfig.setMinIdle(Integer.parseInt(props.getProperty("redis.poolConfig.min.idle")));
		else
			poolConfig.setMinIdle(DEFAULT_MIN_IDLE);

		if (props.containsKey("redis.poolConfig.max.waitMillis"))
			poolConfig.setMaxWaitMillis(Long.parseLong(props.getProperty("redis.poolConfig.max.waitMillis")));
		else
			poolConfig.setMaxWaitMillis(DEFAULT_MAX_WAITMILLIS);

		load();
	}
	/**
	 * 初始化连接池
	 */
	private static void load() {
		if (pool != null)
			pool.destroy();
		String addr = twemproxys.poll();
		twemproxys.offer(addr);
		String[] addrs = addr.split(":");

		pool = new JedisPool(poolConfig, addrs[0], Integer.parseInt(addrs[1]));
	}
	/**
	 * 获取池对象
	 * 
	 * @return 池对象
	 */
	public static Jedis getJedis() {
		Jedis jedis = null;
		for (int i = 0; twemproxys != null && i < twemproxys.size() && jedis == null; i++) {
			try {
				jedis = pool.getResource();
			} catch (JedisException e) {
				logger.error("get jedis error", e);
			}
			// 重新初始化一个新地址的连接池
			if (jedis == null)
				load();
		}

		return jedis;
	}

	/**
	 * 释放池对象
	 * 
	 * @param resource
	 *            池对象
	 */
	public static void returnResource(Jedis resource) {
		if (pool != null)
			pool.returnResource(resource);
	}

	// =======================ALL=======================

	@Override
	public Boolean exists(Jedis jedis, String key) {
		return jedis.exists(key);
	}

	@Override
	public Long del(Jedis jedis, String key) {
		return jedis.del(key);
	}

	@Override
	public Long del(Jedis jedis, String... keys) {
		return jedis.del(keys);
	}

	@Override
	public Long expire(Jedis jedis, String key, int seconds) {
		return jedis.expire(key, seconds);
	}

	@Override
	public Long expireAt(Jedis jedis, String key, long unixTime) {
		return jedis.expireAt(key, unixTime);
	}

	@Override
	public Long ttl(Jedis jedis, String key) {
		return jedis.ttl(key);
	}

	// =======================String=======================

	@Override
	public String setString(Jedis jedis, String key, String value, int seconds) {
		return jedis.setex(key, seconds, value);
	}

	@Override
	public String setString(Jedis jedis, String key, String value) {
		return jedis.set(key, value);
	}

    @Override
    public String setString(Jedis jedis, byte[] key, byte[] value) {
        return jedis.set(key, value);
    }

    @Override
	public Long setStrings(Jedis jedis, String... keysvalues) {
		String flag = null;
		Long count = 0l;
		for (int i = 0; i < keysvalues.length; i += 2) {
			flag = jedis.set(keysvalues[i], keysvalues[i + 1]);
			if (flag != null && flag.equals("OK"))
				count++;
		}
		return count;
	}

	@Override
	public Long appendString(Jedis jedis, String key, String value) {
		return jedis.append(key, value);
	}

	@Override
	public String getString(Jedis jedis, String key) {
		return jedis.get(key);
	}

    @Override
    public byte[] getString(Jedis jedis, byte[] key) {
        return jedis.get(key);
    }

    @Override
	public List<String> getStrings(Jedis jedis, String... keys) {
		return jedis.mget(keys);
	}

	// =======================byte[]=======================

	@Override
	public String setBytes(Jedis jedis, String key, byte[] value, int seconds) {
		try {
			return jedis.setex(key.getBytes("UTF-8"), seconds, value);
		} catch (UnsupportedEncodingException e) {
			logger.error("setBytes error", e);
			return null;
		}
	}

	@Override
	public String setBytes(Jedis jedis, String key, byte[] value) {
		try {
			return jedis.set(key.getBytes("UTF-8"), value);
		} catch (UnsupportedEncodingException e) {
			logger.error("setBytes error", e);
			return null;
		}
	}

	@Override
	public Long appendBytes(Jedis jedis, String key, byte[] value) {
		try {
			return jedis.append(key.getBytes("UTF-8"), value);
		} catch (UnsupportedEncodingException e) {
			logger.error("appendBytes error", e);
			return null;
		}
	}

	@Override
	public byte[] getBytes(Jedis jedis, String key) {
		try {
			return jedis.get(key.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("getBytes error", e);
			return null;
		}
	}

	@Override
	public List<byte[]> getByteses(Jedis jedis, String... keys) {
		byte[][] byteses = new byte[keys.length][];
		for (int i = 0; i < keys.length; i++) {
			try {
				byteses[i] = keys[i].getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("getByteses error", e);
				return null;
			}
		}
		return jedis.mget(byteses);
	}

	// =======================Object=======================

	@Override
	public String setObject(Jedis jedis, String key, Object obj, int seconds) {
		String value = JSON.toJSONString(obj);
		return jedis.setex(key, seconds, value);
	}

	@Override
	public String setObject(Jedis jedis, String key, Object obj) {
		String value = JSON.toJSONString(obj);
		return jedis.set(key, value);
	}

	public String setObject(Jedis jedis, String key, CommonJsonBase obj, int seconds) {
		String value = obj.convertToString();
		return jedis.setex(key, seconds, value);
	};

	public String setObject(Jedis jedis, String key, CommonJsonBase obj) {
		String value = obj.convertToString();
		return jedis.set(key, value);
	};

	@Override
	public <T> T getObject(Jedis jedis, String key, Class<T> clazz) {
		String value = jedis.get(key);
		return JSON.parseObject(value, clazz);
	}

	// =======================Map=======================

	@Override
	public String setMap(Jedis jedis, String key, Map<String, String> hash) {
		return jedis.hmset(key, hash);
	}

    @Override
    public void resetMap(Jedis jedis, String key, Map<String, String> hash) {
//        Transaction tx = jedis.multi();
        jedis.del(key);
        jedis.hmset(key, hash);
//        tx.exec();
    }

    @Override
    public String setMap(Jedis jedis, byte[] key, Map<byte[], byte[]> hash) {
        return jedis.hmset(key, hash);
    }

    @Override
    public void resetMap(Jedis jedis, byte[] key, Map<byte[], byte[]> hash) {
//        Transaction tx = jedis.multi();
        jedis.del(key);
        jedis.hmset(key, hash);
//        tx.exec();
    }

    @Override
	public Long setMapValByKey(Jedis jedis, String key, String mapKey, String value) {
		return jedis.hset(key, mapKey, value);
	}

    @Override
    public boolean hexists(Jedis jedis, String key, String mapKey) {
        return jedis.hexists(key, mapKey);
    }

    @Override
    public Long setMapValByKey(Jedis jedis, byte[] key, byte[] mapKey, byte[] value) {
        return jedis.hset(key, mapKey, value);
    }

    @Override
    public List<byte[]> getMapVals(Jedis jedis, byte[] key) {
        return jedis.hvals(key);
    }

    @Override
	public Set<String> getMapKeys(Jedis jedis, String key) {
		return jedis.hkeys(key);
	}

	@Override
	public List<String> getMapVals(Jedis jedis, String key) {
		return jedis.hvals(key);
	}

	@Override
	public Map<String, String> getMap(Jedis jedis, String key) {
		return jedis.hgetAll(key);
	}

    @Override
    public Map<byte[], byte[]> getMap(Jedis jedis, byte[] key) {
        return jedis.hgetAll(key);
    }

    @Override
	public String getMapValByKey(Jedis jedis, String key, String mapKey) {
		return jedis.hget(key, mapKey);
	}

    @Override
    public byte[] getMapValByKey(Jedis jedis, byte[] key, byte[] mapKey) {
        return jedis.hget(key, mapKey);
    }

    @Override
	public List<String> getMapValsByKeys(Jedis jedis, String key, String... mapKeys) {
		return jedis.hmget(key, mapKeys);
	}

	@Override
	public Long getMapSize(Jedis jedis, String key) {
		return jedis.hlen(key);
	}

	@Override
	public Long delMapValsByKeys(Jedis jedis, String key, String... mapKeys) {
		return jedis.hdel(key, mapKeys);
	}

	@Override
	public Boolean containsKey(Jedis jedis, String key, String mapKey) {
		return jedis.hexists(key, mapKey);
	}

	// =======================List=======================

	@Override
	public Long setList(Jedis jedis, String key, String... values) {
		return jedis.rpush(key, values);
	}

	@Override
	public List<String> getList(Jedis jedis, String key) {
		return jedis.lrange(key, 0, -1);
	}

	@Override
	public Long getListSize(Jedis jedis, String key) {
		return jedis.llen(key);
	}

	@Override
	public String delListEnd(Jedis jedis, String key) {
		return jedis.rpop(key);
	}

	// =======================Set=======================

	@Override
	public Long setSet(Jedis jedis, String key, String... members) {
		return jedis.sadd(key, members);
	}

	@Override
	public Set<String> getSet(Jedis jedis, String key) {
		return jedis.smembers(key);
	}

	@Override
	public Long getSetSize(Jedis jedis, String key) {
		return jedis.scard(key);
	}

	@Override
	public Long delSetMembers(Jedis jedis, String key, String... members) {
		return jedis.srem(key, members);
	}

	@Override
	public String spop(Jedis jedis, String key) {
		return jedis.spop(key);
	}

	@Override
	public Long setnx(Jedis jedis, String key, String value) {
		return jedis.setnx(key, value);
	}

	@Override
	public String getSet(Jedis jedis, String key, String value) {
		return jedis.getSet(key, value);
	}

    // =======================sorted set===================
    @Override
    public Set<String> zrevrangeByScore(Jedis jedis, String key, double max, double min) {
        return jedis.zrevrangeByScore(key, max, min);
    }

    @Override
    public Set<byte[]> zrevrangeByScore(Jedis jedis, byte[] key, double max, double min) {
        return jedis.zrevrangeByScore(key, max, min);
    }

    @Override
    public Set<String> zrange(Jedis jedis, String key, long start, long end) {
        return jedis.zrange(key, start, end);
    }

    @Override
    public Set<byte[]> zrange(Jedis jedis, byte[] key, long start, long end) {
        return jedis.zrange(key, start, end);
    }

    @Override
    public Double zscore(Jedis jedis, String key, String member) {
        return jedis.zscore(key, member);
    }

    @Override
    public Set<byte[]> zrevrange(Jedis jedis, byte[] key, long start, long end) {
        return jedis.zrevrange(key, start, end);
    }

    // =======================Number=======================

	@Override
	public Long incrBy(Jedis jedis, String key, long value) {
		return jedis.incrBy(key, value);
	}

	@Override
	public Double incrByFloat(Jedis jedis, String key, double value) {
		return jedis.incrByFloat(key, value);
	}

	@Override
	public Long hincrBy(Jedis jedis, String key, String field, long value) {
		return jedis.hincrBy(key, field, value);
	}

    @Override
    public String brpoplpush(Jedis jedis, String source, String destination, int timeout) {
        return jedis.brpoplpush(source, destination, timeout);
    }

    @Override
     public String rpoplpush(Jedis jedis, String source, String destination) {
        return jedis.rpoplpush(source, destination);
    }

    @Override
    public byte[] rpoplpush(Jedis jedis, byte[] source, byte[] destination) {
        return jedis.rpoplpush(source, destination);
    }

    @Override
    public Long lrem(Jedis jedis, String key, long count, String value) {
        return jedis.lrem(key, count, value);
    }

    @Override
    public Long lpush(Jedis jedis, String key, String value) {
        return jedis.lpush(key, value);
    }

    @Override
    public Long rpush(Jedis jedis, String key, String value) {
        return jedis.rpush(key, value);
    }

    @Override
    public List<String> lrange(Jedis jedis, String key, long start, long end) {
        return jedis.lrange(key, start, end);
    }

    @Override
    public long llen(Jedis jedis, String key) {
        return jedis.llen(key);
    }

    @Override
    public String lpop(Jedis jedis, String key) {
        return jedis.lpop(key);
    }

    @Override
    public Long lpush(Jedis jedis, byte[] key, byte[] value) {
        return jedis.lpush(key, value);
    }

    @Override
    public Long rpush(Jedis jedis, byte[] key, byte[] value) {
        return jedis.rpush(key, value);
    }

    @Override
    public byte[] lpop(Jedis jedis, byte[] key) {
        return jedis.lpop(key);
    }

    @Override
    public long zadd(Jedis jedis, String key, double score, String member) {
        return jedis.zadd(key, score, member);
    }

    @Override
    public long zadd(Jedis jedis, String key, Map<String, Double> scoreMembers) {
        return jedis.zadd(key, scoreMembers);
    }

    @Override
    public long zrem(Jedis jedis, String key, String member) {
        return jedis.zrem(key, member);
    }
}
