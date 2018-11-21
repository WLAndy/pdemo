package com.betazf.utils.param;

import com.betazf.core.ParamValidation;
import com.betazf.core.ServiceException;

import java.lang.reflect.Field;

public class ParamUtils {


    /**
     * 参数验证
     * @param object
     */
    public static void paramValidation(Object object) {
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ParamValidation paramValidation = field.getAnnotation(ParamValidation.class);
            if (paramValidation != null && paramValidation.validation()){
                throw new ServiceException(paramValidation.paramMessage());
            }
        }
    }
}
