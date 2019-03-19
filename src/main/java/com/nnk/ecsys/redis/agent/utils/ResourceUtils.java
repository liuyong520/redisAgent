package com.nnk.ecsys.redis.agent.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * Created by YHT on 2016/9/18.
 */
public class ResourceUtils {
    /**
     * 根据文件路径模式获取第一个文件源
     *
     * @param locationPattern
     * @return
     */
    public static Resource getResource(String locationPattern) {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(locationPattern);
            if (resources != null && resources.length > 0) {
                return resources[0];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * 根据文件路径模式获取最后一个文件源
     *
     * @param locationPattern
     * @return
     */
    public static Resource getLastResource(String locationPattern) {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(locationPattern);
            if (resources != null && resources.length > 0) {
                return resources[resources.length - 1];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 根据文件路径模式获取所有文件源
     *
     * @param locationPattern
     * @return
     */
    public static Resource[] getResources(String locationPattern) {
        try {
            return new PathMatchingResourcePatternResolver().getResources(locationPattern);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取绝对路径
     *
     * @param locationPattern
     * @return
     */
    public static String getAbsolutePath(String locationPattern) {
        Resource resource = getResource(locationPattern);
        if (null != resource && resource.exists()) {
            try {
                return resource.getFile().getAbsolutePath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 获取绝对路径
     *
     * @param locationPattern
     * @return
     */
    public static String getLastAbsolutePath(String locationPattern) {
        Resource resource = getLastResource(locationPattern);
        if (null != resource && resource.exists()) {
            try {
                return resource.getFile().getAbsolutePath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
