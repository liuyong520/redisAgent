package com.nnk.ecsys.redis.agent.model;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

public class CommonJsonBase implements CommonJsonInterface {
    /**
     * Json字符串解析为对象
     *
     * @param clazz
     * @param data
     * @param <T>
     * @return
     */
    public static <T extends CommonJsonInterface> T convertFromString(Class<T> clazz, String data) {
        try {
            T ret = clazz.newInstance();
            if(!StringUtils.isBlank(data)) {
                ret = JSON.parseObject(data, clazz);
            }
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转Json字符串
     *
     * @return
     */
    public String convertToString() {
        return JSON.toJSONString(this);
    }
}
