package com.zy.manage.im.api;

import com.zy.manage.im.enmu.User;
import com.zy.manage.im.entity.ConfigValue;
import com.zy.manage.im.http.ImHttpHelp;

import java.util.Map;
import java.util.HashMap;

public class ImUserApi extends BaseApi {


    /**
     * 创建网易云通信ID
     *
     * @param configValue 密钥参数
     * @param accid       网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param name        名字
     * @param props       json属性，第三方可选填，最大长度1024字符
     * @param icon        网易云通信ID头像URL，第三方可选填，最大长度1024
     * @param token       网易云通信ID可以指定登录token值，最大长度128字符，并更新，如果未指定，会自动生成token，并在创建成功后返回
     * @param sign        用户签名，最大长度256字符
     * @param email       用户email，最大长度64字符
     * @param birth       用户生日，最大长度16字符
     * @param mobile      用户mobile，最大长度32字符
     * @param gender      用户性别，0表示未知，1表示男，2女表示女，其它会报参数错误
     * @param ex          用户名片扩展字段，最大长度1024字符，用户可自行扩展，建议封装成JSON字符串
     *                    1.第三方帐号导入到网易云通信平台； 2.注意accid，name长度以及考虑管理token。
     * @return
     */
    public static Map<String,Object> userCreate(ConfigValue configValue, String accid, String name, String props, String icon, String token,
                                    String sign, String email, String birth, String mobile, String gender, String ex) {
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("accid", accid);
        bodyParams.put("name", name);
        bodyParams.put("props", props);
        bodyParams.put("icon", icon);
        bodyParams.put("token", token);
        bodyParams.put("sign", sign);
        bodyParams.put("email", email);
        bodyParams.put("birth", birth);
        bodyParams.put("mobile", mobile);
        bodyParams.put("gender", gender);
        bodyParams.put("ex", ex);
        String result = ImHttpHelp.doPost(User.CREATE.getUrl(), getHeaderParams(configValue), bodyParams);
        return jsonStrToMap(result);
    }


    /**
     * 网易云通信ID更新
     *
     * @param configValue 密钥参数
     * @param accid       网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param props       json属性，第三方可选填，最大长度1024字符
     * @param token       网易云通信ID可以指定登录token值，最大长度128字符，并更新，如果未指定，会自动生成token，并在创建成功后返回
     *                    网易云通信ID基本信息更新
     * @return
     */
    public static Map<String,Object> userUpdate(ConfigValue configValue, String accid, String props, String token) {
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("accid", accid);
        bodyParams.put("token", token);
        bodyParams.put("props", props);
        String result = ImHttpHelp.doPost(User.UPDATE.getUrl(), getHeaderParams(configValue), bodyParams);
        return jsonStrToMap(result);
    }

    /**
     * 更新并获取新token
     *
     * @param configValue 网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param accid       1.webserver更新网易云通信ID的token，同时返回新的token；2.一般用于网易云通信ID修改密码，找回密码或者第三方有需求获取新的token。
     * @return
     */
    public static Map<String,Object> userRefreshToken(ConfigValue configValue, String accid) {
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("accid", accid);
        String result = ImHttpHelp.doPost(User.REFRESHTOKEN.getUrl(), getHeaderParams(configValue), bodyParams);
        return jsonStrToMap(result);
    }


    /**
     * 封禁网易云通信ID
     *
     * @param configValue 密钥参数
     * @param accid       网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     * @param needkick    是否踢掉被禁用户，true或false，默认false
     *                    1.第三方禁用某个网易云通信ID的IM功能；2.封禁网易云通信ID后，此ID将不能登陆网易云通信imserver。
     * @return
     */
    public static Map<String,Object> userBlock(ConfigValue configValue, String accid, String needkick) {
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("accid", accid);
        bodyParams.put("needkick", needkick);
        String result = ImHttpHelp.doPost(User.BLOCK.getUrl(), getHeaderParams(configValue), bodyParams);
        return jsonStrToMap(result);
    }


    /**
     * 解禁网易云通信ID
     *
     * @param configValue 密钥参数
     * @param accid       网易云通信ID，最大长度32字符，必须保证一个APP内唯一
     *                    解禁被封禁的网易云通信ID。
     * @return
     */
    public static Map<String,Object> userUnblock(ConfigValue configValue, String accid) {
        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("accid", accid);
        String result = ImHttpHelp.doPost(User.UNBLOCK.getUrl(), getHeaderParams(configValue), bodyParams);
        return jsonStrToMap(result);
    }


}
