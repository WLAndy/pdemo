package com.betazf.utils;

import com.alibaba.fastjson.JSONObject;
import com.betazf.core.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class JsonConvert {

    static Logger logger = LoggerFactory.getLogger(JsonConvert.class);

    /**
     * json转Map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        if (StringUtils.isEmpty(json))
            throw new ServiceException("json数据为空");
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(json);
        } catch (Exception e) {
            logger.error("String 转 Json对象异常");
            e.printStackTrace();
        }
        if (jsonObject == null)
            throw new ServiceException("json格式有误，请检查数据");
        Map<String, Object> result = jsonObject.toJavaObject(Map.class);
        return result;
    }

    /**
     * json 转 对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> clazz){
        if (StringUtils.isEmpty(json))
            throw new ServiceException("json数据为空");
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(json);
        } catch (Exception e) {
            logger.error("String 转 Json对象异常");
            e.printStackTrace();
        }
        T t = jsonObject.toJavaObject(clazz);
        return t;
    }


    /**
     * 判断是不是基本类型
     *
     * @param c
     * @return
     */
    public static boolean isBasicType(Class c) {
        if (c.equals(java.lang.Integer.class) ||
                c.equals(java.lang.Byte.class) ||
                c.equals(java.lang.Long.class) ||
                c.equals(java.lang.Double.class) ||
                c.equals(java.lang.Float.class) ||
                c.equals(java.lang.Character.class) ||
                c.equals(java.lang.Short.class) ||
                c.equals(java.lang.Boolean.class) ||
                c.equals(byte.class) ||
                c.equals(short.class) ||
                c.equals(int.class) ||
                c.equals(long.class) ||
                c.equals(float.class) ||
                c.equals(double.class) ||
                c.equals(boolean.class) ||
                c.equals(char.class)) {
            return true;
        }
        return false;
    }
}
