package com.zy.manage.im;

import com.zy.manage.im.api.ImUserApi;
import com.zy.manage.im.entity.ConfigValue;

import java.util.Map;

public class Test {


    public static void main(String args[]){

        ConfigValue configValue = new ConfigValue();
        Map<String, Object> map = ImUserApi.userCreate(configValue, "abc6699887", null, null, null, null, null, null, null, null, null, null);
        System.err.println(map);
    }
}
