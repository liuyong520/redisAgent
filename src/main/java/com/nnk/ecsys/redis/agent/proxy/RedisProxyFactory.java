package com.nnk.ecsys.redis.agent.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理工厂
 * 
 * @author 张浩 2015-1-7
 */
public class RedisProxyFactory {
	private static final Logger logger = LoggerFactory.getLogger(RedisProxyFactory.class);

	/**
	 * 初始化代理
	 * 
	 * @return 代理对象
	 */
	public static IRedis getInstance() {
		IRedis proxy = null;
		IRedis delegate = null;
		try {
			delegate = new Redis();
		} catch (Exception e) {
			logger.warn("无可用配置文件，redis初始化失败!", e);
			return null;
		}
		InvocationHandler handler = new RedisInvocationHandler(delegate);
		proxy = (IRedis) Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), handler);
		return proxy;
	}

}
