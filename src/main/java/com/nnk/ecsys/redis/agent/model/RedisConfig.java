package com.nnk.ecsys.redis.agent.model;

/**
 * Created by YHT on 2017/11/14.
 */
public class RedisConfig {
    private String[] addresses;     // redis地址
    private int maxTotal;           // 最大连接数
    private int minIdle;            // 最小空闲连接数量
    private int maxIdle;            // 最大空闲连接数量
    private int waitMillis;         // 获取连接时最大等待时间，单位：毫秒

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getWaitMillis() {
        return waitMillis;
    }

    public void setWaitMillis(int waitMillis) {
        this.waitMillis = waitMillis;
    }
}
