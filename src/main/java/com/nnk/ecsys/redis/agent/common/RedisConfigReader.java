package com.nnk.ecsys.redis.agent.common;

import com.nnk.ecsys.redis.agent.model.RedisConfig;
import com.nnk.ecsys.redis.agent.utils.ResourceUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by YHT on 2017/11/14.
 */
public class RedisConfigReader {
    public static RedisConfig readConfig(String resourcePath) {
        return readConfig(ResourceUtils.getLastResource(resourcePath));
    }

    public static RedisConfig readConfig(Resource resource) {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            RedisConfig redisConfig = new RedisConfig();
            redisConfig.setAddresses(properties.getProperty("redis.twemproxy.addresses").trim().split(","));
            redisConfig.setMaxTotal(Integer.valueOf(properties.getProperty("redis.poolConfig.max.total").trim()));
            redisConfig.setMinIdle(Integer.valueOf(properties.getProperty("redis.poolConfig.max.idle").trim()));
            redisConfig.setMaxIdle(Integer.valueOf(properties.getProperty("redis.poolConfig.min.idle").trim()));
            redisConfig.setWaitMillis(Integer.valueOf(properties.getProperty("redis.poolConfig.max.waitMillis").trim()));
            return redisConfig;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
