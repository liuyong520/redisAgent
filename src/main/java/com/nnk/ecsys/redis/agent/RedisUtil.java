package com.nnk.ecsys.redis.agent;

import com.nnk.ecsys.redis.agent.model.CommonJsonBase;
import com.nnk.ecsys.redis.agent.proxy.IRedis;
import com.nnk.ecsys.redis.agent.proxy.RedisProxyFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis工具类
 *
 * @author 张浩 2015-1-7
 */
public class RedisUtil {

    private static final IRedis proxy;

    static {
        proxy = RedisProxyFactory.getInstance();
    }

    // =======================ALL=======================

    /**
     * 检查键是否存在
     *
     * @param key
     * @return 是否存在
     */
    public static Boolean exists(String key) {
        return proxy.exists(null, key);
    }

    /**
     * 删除一个键值
     *
     * @param key
     * @return 删除个数
     */
    public static Long del(String key) {
        return proxy.del(null, key);
    }

    /**
     * 删除多个键值
     *
     * @param keys
     * @return 删除个数
     */
    public static Long del(String... keys) {
        return proxy.del(null, keys);
    }

    /**
     * 设置到期时间
     *
     * @param key
     * @param seconds
     *            到期时间(秒)
     * @return 1: the timeout was set. 0: the timeout was not set since the key already has an associated timeout , or
     *         the key does not exist
     */
    public static Long expire(String key, int seconds) {
        return proxy.expire(null, key, seconds);
    }

    /**
     * 设置到期时间
     *
     * @param key
     * @param unixTime
     *            到期时间(自 1970 年 1 月 1 日 00:00:00 GMT 以来此时间对象表示的秒数)
     * @return 1: the timeout was set. 0: the timeout was not set since the key already has an associated timeout , or
     *         the key does not exist
     */
    public static Long expireAt(String key, long unixTime) {
        return proxy.expireAt(null, key, unixTime);
    }

    /**
     * 获取到期时间
     *
     * @param key
     * @return 没有设置到期时间则返回-1,否则返回还有多少秒到期
     */
    public static Long ttl(String key) {
        return proxy.ttl(null, key);
    }

    // =======================String=======================

    /**
     * 插入一个键值,存在则覆盖原值
     *
     * @param key
     * @param value
     * @param seconds
     *            到期时间(秒)
     * @return OK 表示成功
     */
    public static String setString(String key, String value, int seconds) {
        return proxy.setString(null, key, value, seconds);
    }

    /**
     * 插入一个键值,存在则覆盖原值
     *
     * @param key
     * @param value
     * @return OK 表示成功
     */
    public static String setString(String key, String value) {
        return proxy.setString(null, key, value);
    }

    /**
     * 插入一个键值,存在则覆盖原值
     *
     * @param key
     * @param value
     * @return OK 表示成功
     */
    public static String setString(byte[] key, byte[] value) {
        return proxy.setString(null, key, value);
    }

    /**
     * 插入多个键值,存在则覆盖原值,形式:key1,val1,key2,val2,...
     *
     * @param keysvalues
     * @return 插入成功个数
     */
    public static Long setStrings(String... keysvalues) {
        return proxy.setStrings(null, keysvalues);
    }

    /**
     * 在键对应值的末尾拼接新值,不存在则以此创建新键值
     *
     * @param key
     * @param value
     * @return 拼接后的字符串长度
     */
    public static Long appendString(String key, String value) {
        return proxy.appendString(null, key, value);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return String类型值
     */
    public static String getString(String key) {
        return proxy.getString(null, key);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return String类型值
     */
    public static byte[] getString(byte[] key) {
        return proxy.getString(null, key);
    }

    /**
     * 获取多个值
     *
     * @param keys
     * @return String类型值List集合
     */
    public static List<String> getStrings(String... keys) {
        return proxy.getStrings(null, keys);
    }

    // =======================byte[]=======================

    /**
     * 插入一个键值,存在则覆盖原值
     *
     * @param key
     * @param value
     * @param seconds
     *            到期时间(秒)
     * @return OK 表示成功
     */
    public static String setBytes(String key, byte[] value, int seconds) {
        return proxy.setBytes(null, key, value, seconds);
    }

    /**
     * 插入一个键值,存在则覆盖原值
     *
     * @param key
     * @param value
     * @return OK 表示成功
     */
    public static String setBytes(String key, byte[] value) {
        return proxy.setBytes(null, key, value);
    }

    /**
     * 在键对应值的末尾拼接新值,不存在则以此创建新键值
     *
     * @param key
     * @param value
     * @return 拼接后的字符串长度
     */
    public static Long appendBytes(String key, byte[] value) {
        return proxy.appendBytes(null, key, value);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return byte[]类型值
     */
    public static byte[] getBytes(String key) {
        return proxy.getBytes(null, key);
    }

    /**
     * 获取多个值
     *
     * @param keys
     * @return byte[]类型值List集合
     */
    public static List<byte[]> getByteses(String... keys) {
        return proxy.getByteses(null, keys);
    }

    // =======================Object=======================

    /**
     * 插入一个键值
     *
     * @param key
     * @param obj
     * @param seconds
     *            到期时间(秒)
     * @return OK 表示成功
     */
    public static String setObject(String key, Object obj, int seconds) {
        return proxy.setObject(null, key, obj, seconds);
    }

    /**
     * 插入一个键值
     *
     * @param key
     * @param obj
     * @return OK 表示成功
     */
    public static String setObject(String key, Object obj) {
        return proxy.setObject(null, key, obj);
    }

    /**
     * 插入一个键值
     *
     * @param key
     * @param obj
     * @param seconds
     *            到期时间(秒)
     * @return OK 表示成功
     */
    public static String setObject(String key, CommonJsonBase obj, int seconds) {
        return proxy.setObject(null, key, obj, seconds);
    }

    /**
     * 插入一个键值
     *
     * @param key
     * @param obj
     * @return OK 表示成功
     */
    public static String setObject(String key, CommonJsonBase obj) {
        return proxy.setObject(null, key, obj);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @param clazz
     *            对象类型
     * @return 对象类型的值
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        return proxy.getObject(null, key, clazz);
    }

    // =======================Map=======================

    /**
     * 插入一个键值
     *
     * @param key
     * @param hash
     * @return OK 表示成功
     */
    public static String setMap(String key, Map<String, String> hash) {
        return proxy.setMap(null, key, hash);
    }

    /**
     * 插入一个键值
     *
     * @param key
     * @param hash
     * @return OK 表示成功
     */
    public static void resetMap(String key, Map<String, String> hash) {
        proxy.resetMap(null, key, hash);
    }

    /**
     * 插入一个键值
     *
     * @param key
     * @param hash
     * @return OK 表示成功
     */
    public static String setMap(byte[] key, Map<byte[], byte[]> hash) {
        return proxy.setMap(null, key, hash);
    }

    /**
     * 插入一个键值
     *
     * @param key
     * @param hash
     * @return OK 表示成功
     */
    public static void resetMap(byte[] key, Map<byte[], byte[]> hash) {
        proxy.resetMap(null, key, hash);
    }

    /**
     * 在键对应map中添加新键值,不存在则以此创建新map
     *
     * @param key
     * @param mapKey
     * @param value
     * @return map中插入新键值个数
     */
    public static Long setMapValByKey(String key, String mapKey, String value) {
        return proxy.setMapValByKey(null, key, mapKey, value);
    }

    /**
     * 检测map中的key是否存在
     * @param key
     * @param mapKey
     * @return
     */
    public static boolean hexists(String key, String mapKey) {
        return proxy.hexists(null, key, mapKey);
    }

    /**
     * 在键对应map中添加新键值,不存在则以此创建新map
     *
     * @param key
     * @param mapKey
     * @param value
     * @return map中插入新键值个数
     */
    public static Long setMapValByKey(byte[] key, byte[] mapKey, byte[] value) {
        return proxy.setMapValByKey(null, key, mapKey, value);
    }

    /**
     * 获取map中所有键
     *
     * @param key
     * @return map的String类型键Set集合
     */
    public static Set<String> getMapKeys(String key) {
        return proxy.getMapKeys(null, key);
    }

    /**
     * 获取map中所有值
     *
     * @param key
     * @return map的String类型值List集合
     */
    public static List<byte[]> getMapVals(byte[] key) {
        return proxy.getMapVals(null, key);
    }

    /**
     * 获取map中所有值
     *
     * @param key
     * @return map的String类型值List集合
     */
    public static List<String> getMapVals(String key) {
        return proxy.getMapVals(null, key);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return Map<String,String>类型值
     */
    public static Map<String, String> getMap(String key) {
        return proxy.getMap(null, key);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return Map<byte[],byte[]>类型值
     */
    public static Map<byte[], byte[]> getMap(byte[] key) {
        return proxy.getMap(null, key);
    }

    /**
     * 获取map中一个键对应的值
     *
     * @param key
     * @param mapKey
     * @return map的String类型值
     */
    public static String getMapValByKey(String key, String mapKey) {
        return proxy.getMapValByKey(null, key, mapKey);
    }

    /**
     * 获取map中一个键对应的值
     *
     * @param key
     * @param mapKey
     * @return map的String类型值
     */
    public static byte[] getMapValByKey(byte[] key, byte[] mapKey) {
        return proxy.getMapValByKey(null, key, mapKey);
    }

    /**
     * 获取map中多个键对应的值
     *
     * @param key
     * @param mapKeys
     * @return map的String类型值List集合
     */
    public static List<String> getMapValsByKeys(String key, String... mapKeys) {
        return proxy.getMapValsByKeys(null, key, mapKeys);
    }

    /**
     * 获取map中键值个数
     *
     * @param key
     * @return map中键值个数
     */
    public static Long getMapSize(String key) {
        return proxy.getMapSize(null, key);
    }

    /**
     * 删除map中多个键值
     *
     * @param key
     * @param mapKeys
     * @return 删除map中键值个数
     */
    public static Long delMapValsByKeys(String key, String... mapKeys) {
        return proxy.delMapValsByKeys(null, key, mapKeys);
    }

    /**
     * 检查map中键是否存在
     *
     * @param key
     * @param mapKey
     * @return 是否存在
     */
    public static Boolean containsKey(String key, String mapKey) {
        return proxy.containsKey(null, key, mapKey);
    }

    // =======================List=======================

    /**
     * 在键对应list尾部添加新元素,不存在则以此创建新list,元素可以重复
     *
     * @param key
     * @param values
     * @return list添加新元素后的长度
     */
    public static Long setList(String key, String... values) {
        return proxy.setList(null, key, values);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return List<String>类型值
     */
    public static List<String> getList(String key) {
        return proxy.getList(null, key);
    }

    /**
     * 获取list中元素个数
     *
     * @param key
     * @return list中元素个数
     */
    public static Long getListSize(String key) {
        return proxy.getListSize(null, key);
    }

    /**
     * 删除list尾部元素
     *
     * @param key
     * @return 删除的元素
     */
    public static String delListEnd(String key) {
        return proxy.delListEnd(null, key);
    }

    // =======================Set=======================

    /**
     * 在键对应set中添加新元素,不存在则以此创建新set,元素不可以重复
     *
     * @param key
     * @param members
     * @return set中添加的新元素个数
     */
    public static Long setSet(String key, String... members) {
        return proxy.setSet(null, key, members);
    }

    /**
     * 获取一个值
     *
     * @param key
     * @return Set<String>类型值
     */
    public static Set<String> getSet(String key) {
        return proxy.getSet(null, key);
    }

    /**
     * 获取set中元素个数
     *
     * @param key
     * @return set中元素个数
     */
    public static Long getSetSize(String key) {
        return proxy.getSetSize(null, key);
    }

    /**
     * 删除set中多个元素
     *
     * @param key
     * @param members
     * @return set中删除的元素个数
     */
    public static Long delSetMembers(String key, String... members) {
        return proxy.delSetMembers(null, key, members);
    }

    /**
     * 随机获取并删除一个元素
     *
     * @param key
     * @return 获取并删除的元素
     */
    public static String spop(String key) {
        return proxy.spop(null, key);
    }

    /**
     * 如果键对应的set没有设置值,则在键对应set中添加元素
     *
     * @param key
     * @return 1 if the key was set 0 if the key was not set
     */
    public static Long setnx(String key, String value) {
        return proxy.setnx(null, key, value);
    }

    /**
     * 在键对应set中添加新元素,返回旧元素
     *
     * @param key
     * @return 旧元素
     */
    public static String getSet(String key, String value) {
        return proxy.getSet(null, key, value);
    }

    // =======================Number=======================

    /**
     * 整型累加,负数累减
     *
     * @param key
     * @param value
     * @return 累加后的值
     */
    public static Long incrBy(String key, long value) {
        return proxy.incrBy(null, key, value);
    }

    /**
     * 浮点型累加,负数累减
     *
     * @param key
     * @param value
     * @return 累加后的值
     */
    public static Double incrByFloat(String key,double value) {
        return proxy.incrByFloat(null, key, value);
    }

    public static Set<String> zrevrangeByScore(String key, double max, double min) {
        return proxy.zrevrangeByScore(null, key, max, min);
    }

    public static Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return proxy.zrevrangeByScore(null, key, max, min);
    }

    public static Set<byte[]> zrange(byte[] key, long start, long end) {
        return proxy.zrange(null, key, start, end);
    }

    public static Set<String> zrange(String key, long start, long end) {
        return proxy.zrange(null, key, start, end);
    }

    public static Double zscore(String key, String member) {
        return proxy.zscore(null, key, member);
    }

    public static Set<byte[]> zrevrange(String key, long start, long end) {
        return proxy.zrevrange(null, key.getBytes(), start, end);
    }

    /**
     * 对Map成员进行累加,整型累加,负数累减
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Long hincrBy(String key,String field, long value) {
        return proxy.hincrBy(null, key,field, value);
    }

    public static String brpoplpush(String source, String destination, int timeout) {
        return proxy.brpoplpush(null, source, destination, timeout);
    }

    public static String rpoplpush(String source, String destination) {
        return proxy.rpoplpush(null, source, destination);
    }

    public static byte[] rpoplpush(byte[] source, byte[] destination) {
        return proxy.rpoplpush(null, source, destination);
    }

    public static Long lrem(String key, long count, String value) {
        return proxy.lrem(null, key, count, value);
    }

    public static Long lpush(String key, String value) {
        return proxy.lpush(null, key, value);
    }

    public static Long rpush(String key, String value) {
        return proxy.rpush(null, key, value);
    }

    public static List<String> lrange(String key, long start, long end) {
        return proxy.lrange(null, key, start, end);
    }

    public static long llen(String key) {
        return proxy.llen(null, key);
    }

    public static String lpop(String key) {
        return proxy.lpop(null, key);
    }

    public static Long lpush(byte[] key, byte[] value) {
        return proxy.lpush(null, key, value);
    }

    public static Long rpush(byte[] key, byte[] value) {
        return proxy.rpush(null, key, value);
    }

    public static byte[] lpop(byte[] key) {
        return proxy.lpop(null, key);
    }

    public static long zadd(String key, double score, String member) {
        return proxy.zadd(null, key, score, member);
    }

    public static long zadd(String key, Map<String, Double> scoreMembers) {
        return proxy.zadd(null, key, scoreMembers);
    }

    public static long zrem(String key, String member) {
        return proxy.zrem(null, key, member);
    }
}
