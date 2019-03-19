package com.nnk.ecsys.redis.agent.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * redis代理,统一获取和释放池对象
 * 
 * @author 张浩 2015-1-7
 */
public class RedisInvocationHandler implements InvocationHandler {
	private static final Logger logger = LoggerFactory.getLogger(RedisInvocationHandler.class);

	private final Object delegate;

	public RedisInvocationHandler(Object delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
		Object result = null;

		for (int i = 1; i < params.length; i++) {
			if (params[i] == null)
				return result;
		}

		Jedis jedis = Redis.getJedis();
		if (jedis == null) {
			return result;
		}

		params[0] = jedis;

		try {
			result = method.invoke(delegate, params);
		} catch (Exception e) {
			logger.error("delegate method error", e);
		} finally {
			Redis.returnResource(jedis);
		}

		return result;
	}

}
