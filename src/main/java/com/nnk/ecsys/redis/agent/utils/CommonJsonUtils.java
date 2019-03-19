package com.nnk.ecsys.redis.agent.utils;




import com.nnk.ecsys.redis.agent.model.CommonJsonBase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class CommonJsonUtils {
    /**
     * 覆盖对象属性
     * source属性值为NULL不会覆盖
     * source属性类型为CommonJsonBase，则调用replaceFrom方法进行覆盖处理
     *
     * @param source
     * @param target
     */
    public static void replaceProperties(CommonJsonBase source, CommonJsonBase target) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(source.getClass());
        for (PropertyDescriptor sourcePd : sourcePds) {
            if (null == sourcePd.getReadMethod() || null == sourcePd.getWriteMethod()) {
                continue;
            }
            PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(source.getClass(), sourcePd.getName());
            if (null == targetPd || null == targetPd.getReadMethod() || null == targetPd.getWriteMethod()) {
                continue;
            }
            try {
                Method sourceReadMethod = sourcePd.getReadMethod();
                Object sourceValue = sourceReadMethod.invoke(source);
                if (null == sourceValue) {
                    continue;
                }
                if (CommonJsonBase.class.isAssignableFrom(targetPd.getPropertyType())) {
                    Method targetReadMethod = targetPd.getReadMethod();
                    Object targetValue = targetReadMethod.invoke(target);
                    if (null != targetValue) {
                        replaceProperties((CommonJsonBase) sourceValue, (CommonJsonBase) targetValue);
                        continue;
                    }
                }
                Method targetWriteMethod = targetPd.getWriteMethod();
                targetWriteMethod.invoke(target, sourceValue);
            } catch (Throwable ex) {
                throw new FatalBeanException("Could not copy properties from source to target", ex);
            }
        }
    }

    /**
     * 覆盖对象属性
     * source属性值为NULL不会覆盖
     * source属性类型为CommonJsonBase，则调用replaceFrom方法进行覆盖处理
     *
     * @param clazz
     * @param sourceJson
     * @param targetJson
     * @param <T>
     * @return
     */
    public static <T extends CommonJsonBase> String replaceProperties(Class<T> clazz, String sourceJson, String targetJson) {
        CommonJsonBase source = CommonJsonBase.convertFromString(clazz, sourceJson);
        CommonJsonBase target = CommonJsonBase.convertFromString(clazz, targetJson);
        replaceProperties(source, target);
        return target.convertToString();
    }

}
