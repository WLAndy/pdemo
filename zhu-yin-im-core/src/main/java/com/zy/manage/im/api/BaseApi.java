package com.zy.manage.im.api;

import com.alibaba.fastjson.JSONObject;
import com.zy.manage.im.entity.ConfigValue;
import com.zy.manage.im.utils.CheckSumBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseApi {
    /**
     * 得到公参
     * @param configValue 密钥参数
     * @return
     */
    public static Map<String,String> getHeaderParams(ConfigValue configValue){
        String nonce = UUID.randomUUID().toString();
        String appKey = configValue.getAppKey();
        String appSecret = configValue.getAppSecret();
        String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);
        Map<String,String> headerParams = new HashMap<>();
        headerParams.put("AppKey",appKey);
        headerParams.put("Nonce",nonce);
        headerParams.put("CurTime",curTime);
        headerParams.put("CheckSum",checkSum);
        return headerParams;
    }


    /**
     * 把json串转为map
     * @param jsonString
     * @return
     */
    public static Map<String,Object> jsonStrToMap(String jsonString){
        if (jsonString == null)
            return null;
        Map<String,Object> result = new HashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        jsonObject.forEach((k,v) -> result.put(k,v));
        return result;
    }
}
