package com.nnk.ecsys.redis.agent.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by YHT on 2017/11/14.
 */
public class RedissonUtils {
    private static final String REDIS_PROPERTIES = "classpath*:/properties/redis.properties";
    private static RedissonManager redissonManager = null;

    /**
     * 获取默认锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static RLock getRLock(String lockName, long waitTime, long timeout) {
        return getRLockByLockType(LockType.Lock, lockName, waitTime, timeout);
    }

    /**
     * 获取公平锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static RLock getFairRLock(String lockName, long waitTime, long timeout) {
        return getRLockByLockType(LockType.FairLock, lockName, waitTime, timeout);
    }


    /**
     * 获取读锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static RLock getReadRLock(String lockName, long waitTime, long timeout) {
        return getRLockByLockType(LockType.ReadLock, lockName, waitTime, timeout);
    }


    /**
     * 获取写锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static RLock getWriteRLock(String lockName, long waitTime, long timeout) {
        return getRLockByLockType(LockType.WriteLock, lockName, waitTime, timeout);
    }

    /**
     * 获取默认锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static boolean getLock(String lockName, long waitTime, long timeout) {
        return getLockByLockType(LockType.Lock, lockName, waitTime, timeout);
    }


    /**
     * 获取公平锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static boolean getFairLock(String lockName, long waitTime, long timeout) {
        return getLockByLockType(LockType.FairLock, lockName, waitTime, timeout);
    }


    /**
     * 获取读锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static boolean getReadLock(String lockName, long waitTime, long timeout) {
        return getLockByLockType(LockType.ReadLock, lockName, waitTime, timeout);
    }


    /**
     * 获取写锁
     *
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    public static boolean getWriteLock(String lockName, long waitTime, long timeout) {
        return getLockByLockType(LockType.WriteLock, lockName, waitTime, timeout);
    }

    /**
     * 解锁【重入锁】
     *
     * @param lockName
     * @return
     */
    public static boolean unLock(String lockName) {
        return unlockByLockType(LockType.Lock, lockName);
    }

    /**
     * 解锁【公平锁】
     *
     * @param lockName
     * @return
     */
    public static boolean unFairLock(String lockName) {
        return unlockByLockType(LockType.FairLock, lockName);
    }

    /**
     * 解锁【读锁】
     *
     * @param lockName
     * @return
     */
    public static boolean unReadLock(String lockName) {
        return unlockByLockType(LockType.ReadLock, lockName);
    }

    /**
     * 解锁【写锁】
     *
     * @param lockName
     * @return
     */
    public static boolean unWriteLock(String lockName) {
        return unlockByLockType(LockType.WriteLock, lockName);
    }

    /**
     * 获取锁对象
     *
     * @param lockType
     * @param lockName
     * @return
     */
    private static RLock getRLock(LockType lockType, String lockName) {
        switch (lockType) {
            case Lock:
                return redissonManager.getLock(lockName);
            case FairLock:
                return redissonManager.getFairLock(lockName);
            case ReadLock:
                return redissonManager.getReadLock(lockName);
            case WriteLock:
                return redissonManager.getWriteLock(lockName);
        }
        return null;
    }

    /**
     * 获取锁
     *
     * @param lockType
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    private static RLock getRLockByLockType(LockType lockType, String lockName, long waitTime, long timeout) {
        try {
            RLock rLock = getRLock(lockType, lockName);
            boolean resLock = rLock.tryLock(waitTime, timeout, TimeUnit.SECONDS);
            if (resLock) {
                return rLock;
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取锁
     *
     * @param lockType
     * @param lockName
     * @param waitTime 单位：秒
     * @param timeout 单位：秒
     * @return
     */
    private static boolean getLockByLockType(LockType lockType, String lockName, long waitTime, long timeout) {
        try {
            RLock rLock = getRLock(lockType, lockName);
            return rLock.tryLock(waitTime, timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解锁
     *
     * @param lockType
     * @param lockName
     * @return
     */
    public static boolean unlockByLockType(LockType lockType, String lockName) {
        try {
            RLock rLock = getRLock(lockType, lockName);
            rLock.unlock();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取Redisson
     *
     * @return
     */
    public static RedissonClient getLockRedisson() {
        return redissonManager.getRedisson();
    }

    /**
     * 重载Redisson
     */
    public static void loadRedisson() {
        redissonManager.loadRedisson();
    }

    /**
     * 关闭Redisson
     */
    public static void cloneRedisson() {
        redissonManager.cloneRedisson();
    }

    /**
     * 锁类型
     */
    public static enum LockType {
        Lock,
        FairLock,
        ReadLock,
        WriteLock,
    }

}
