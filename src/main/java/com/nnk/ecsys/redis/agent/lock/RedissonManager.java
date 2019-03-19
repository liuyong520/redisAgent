package com.nnk.ecsys.redis.agent.lock;

import com.nnk.ecsys.redis.agent.model.RedisConfig;
import org.apache.commons.lang.ArrayUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YHT on 2017/11/14.
 */
public class RedissonManager {
    private final Logger logger = LoggerFactory.getLogger(RedissonManager.class);
    private RedissonClient redisson;
    private RedisConfig redisConfig;
    private String currentAddress;

    public RedissonManager(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    /**
     * 获取下一个访问地址
     *
     * @return
     */
    protected String getNextAddress() {
        String[] addressArr = redisConfig.getAddresses();
        if (ArrayUtils.isEmpty(addressArr)) {
            return null;
        }
        if (currentAddress == null) {
            return addressArr[0];
        }
        for (int i = 0; i < addressArr.length; i++) {
            String tmpAddress = addressArr[i];
            if (currentAddress.equals(tmpAddress)) {
                if ((i + 1) < addressArr.length) {
                    return addressArr[i + 1];
                } else {
                    return addressArr[0];
                }
            }
        }
        return addressArr[0];
    }

    /**
     * 重载RedissonClient
     */
    public void loadRedisson() {
        Config config = new Config();
        currentAddress = this.getNextAddress();
        logger.debug(String.format("loadRedisson address:%s,maxTotal:%s,minIdle:%s,waitMillis:%s", currentAddress, redisConfig.getMaxTotal(), redisConfig.getMinIdle(), redisConfig.getWaitMillis()));
        config.useSingleServer()
                .setAddress("redis://" + currentAddress)
                .setConnectionPoolSize(redisConfig.getMaxTotal())
                .setConnectionMinimumIdleSize(redisConfig.getMinIdle())
                .setConnectTimeout(redisConfig.getWaitMillis());
        redisson = Redisson.create(config);
        logger.debug("loadRedisson succeed");
    }

    /**
     * 关闭Redisson
     */
    public void cloneRedisson() {
        if (null != redisson) {
            try {
                redisson.shutdown();
            } catch (Exception e) {
                logger.error("cloneRedisson error:", e);
            }
        }
        redisson = null;
    }

    /**
     * 获取RedissonClient【第一次执行会进行初始化】
     *
     * @return
     */
    public RedissonClient getRedisson() {
        if (null == redisson) {
            synchronized (this) {
                if (null == redisson) {
                    this.loadRedisson();
                }
            }
        }
        return redisson;
    }

    /**
     * 可重入锁
     *
     * @param name
     * @return
     */
    public RLock getLock(String name) {
        return getRedisson().getLock(name);
    }

    /**
     * 公平锁
     *
     * @param name
     * @return
     */
    public RLock getFairLock(String name) {
        return getRedisson().getFairLock(name);
    }

    /**
     * 读锁
     *
     * @param name
     * @return
     */
    public RLock getReadLock(String name) {
        return this.getRedisson().getReadWriteLock(name).readLock();
    }

    /**
     * 写锁
     *
     * @param name
     * @return
     */
    public RLock getWriteLock(String name) {
        return this.getRedisson().getReadWriteLock(name).writeLock();
    }
}
