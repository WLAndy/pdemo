package com.betazf.utils.param;

import com.betazf.core.ParamValidation;
import com.betazf.core.ServiceException;

import java.lang.reflect.Field;

public class ParamUtils {


    /**
     * 参数验证
     *
     * @param object
     */
    public static void paramValidation(Object object) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ParamValidation paramValidation = field.getAnnotation(ParamValidation.class);
            if (paramValidation == null || !paramValidation.validation())
                continue;
            Object o = null;
            field.setAccessible(true);
            try {
                o = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (o == null || o == "" || "".equals(o) || "null".equalsIgnoreCase((String) o))
                throw new ServiceException(paramValidation.paramMessage());
        }
    }
}
