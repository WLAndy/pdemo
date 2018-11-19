package com.zy.manage.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zy.manage.core.ResultGenerator;
import com.zy.manage.utils.HttpClientUtils;
import com.zy.manage.utils.encryption.AES;
import com.zy.manage.utils.encryption.AESEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import javax.crypto.Cipher;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class T {

    //        wx.small.program.appid=wxd281f40f20429565
//        wx.small.program.appsecret=

    public static void main(String args[]) throws IOException {
//        String encryptedData = "Dwrp9/Xjguc7+zjs3vLKf4ffC8SYbc9N2sClzqyVKbOl321bZoc1urtfZ6pQ68VSvFh/agBpG4i+OvbcfuOcbpyiEQ4EWLjmCxk7y2Ppd5LhYAPjSFgg70vfAVglKZNk49ICIuPMje/frWLh2svCT+6CIyBeCwDOnd4WZopbM5yJ3K1+J4xV/emGRe0XrSQXTakII4A8z/vo+o+WJ/PgLIk4joXBL35mN7+w1BJvW/ykAs6RazP2bEYAr7t+W2hN2mdN1/1krUlTTVvfp8BKxIYH62nhWIMW02nDOAiATb+PaRh/bQTmNOlH7IYwLi3y8Kyd5Y7RS7VFyMLk+S4/VTjxUaTUhess5U+2oKAKQlWzYtYbEYf4Ct7pC9YYqsgZRbu+cFPPPhhBu07RBADFHCajKa+Pq7kp0k/Xm5jCdPeMfteqB/YUeMWStKWfTPxwcuVyqFgtr56VQEprcY6e8NUDPIV3Zm1BHb4K/7BiJGeHDRzehrpgiUxr6VbRzJiSASbPbxoqzWkwxjcX0RKY4Q==";
//        String iv = "4Y6Q6G0vW9hB6w4xwB5I5g==";
//        Base64.Decoder decoder = Base64.getDecoder();
//        String session_key = "9jDXWDHm7e3RzAYA1rg5Bw==";
//
//        Map map = new HashMap();
//        try {
//            byte[] resultByte  = AES.decrypt(decoder.decode(encryptedData),
//                    decoder.decode(session_key),
//                    decoder.decode(iv));
//            if(null != resultByte && resultByte.length > 0){
//                String userInfo = new String(resultByte, "UTF-8");
//                map.put("status", "1");
//                map.put("msg", "解密成功");
//                map.put("userInfo", userInfo);
//            }else{
//                map.put("status", "0");
//                map.put("msg", "解密失败");
//            }
//        }catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(map);

//        String data = AESEncoder.encrypt("{\"a\":\"1\",\"b\":\"2\"},{\"string\":\"aabbcc\"}","123456");
//        System.err.println(data);


//        StringBuffer stringBuffer = new StringBuffer();
//        Map<String, Object> resultMap = new HashMap<>();
//        String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/getwxacodeunlimit?access_token=";
//        stringBuffer.append(url);
//        stringBuffer.append(getToken());
//        JSONObject params = new JSONObject();
//        params.put("page","pages/home/home");
//        params.put("scene", "a=1234567890");
//        params.put("width", 430);
//        params.put("auto_color", true);
////        {"page":"pages/home/home", "width": 430}
//        String data = null;
//        try {
//            data = HttpClientUtils.post(stringBuffer.toString(), params.toJSONString(), "application/json", "UTF-8", 10000, 10000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (data == null)
//            System.err.println("获取信息失败");
//        JSONObject jsonObject = JSONObject.parseObject(data);
//        jsonObject.forEach((key, value) -> {
//            if (!key.equals("session_key"))
//                resultMap.put(key, value);
//        });
       String token = getToken();
        getminiqrQr1(token);
    }

    public static String getminiqrQr1(String accessToken){
        String data = null;
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
//        String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
        JSONObject params = new JSONObject();
        params.put("page","pages/home/home");
        params.put("width", 430);
        params.put("scene", "1001");
//        params.put("auto_color", false);
        String jsonString = params.toJSONString();
        try {
            data = HttpClientUtils.post(url,jsonString , "application/json", "UTF-8", 10000, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println(data);
        return null;
    }



    /*
     * 获取 token
     * 普通的 get 可获 token
     */
    public  static String getToken() {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd281f40f20429565&secret=c2b4eb1f4ec81ee3124a6b1674f807c3";
            String rt =  HttpClientUtils.post(url, "", "application/json", "UTF-8", 10000, 10000);
            System.out.println("what is:"+rt);
            JSONObject json = JSONObject.parseObject(rt);
            if (json.getString("access_token") != null || json.getString("access_token") != "") {
                return json.getString("access_token");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 获取 二维码图片
     *
     */
    public static String getminiqrQr( String accessToken) throws IOException {
        String imei ="867186032552993";
        Map<String, Object> params = new HashMap<>();
//        params.put("scene", imei);  //参数
        params.put("page", "pages/home/home"); //位置
        params.put("width", 430);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken);  // 接口
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = JSON.toJSONString(params);           //必须是json模式的 post
        StringEntity entity;
        entity = new StringEntity(body);
        entity.setContentType("image/png");
        httpPost.setEntity(entity);
        HttpResponse response;
        response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        InputStream inputStream = responseEntity.getContent();
        String name = imei+".png";
        saveToImgByInputStream(inputStream,"/Users/andy/Desktop/wx_test_img",name);  //保存图片
        return null;
    }

    /**
     * 将二进制转换成文件保存
     * @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgByInputStream(InputStream instreams,String imgPath,String imgName){
        int stateInt = 1;
        if(instreams != null){
            try {
                File file=new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos=new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
            }
        }
        return stateInt;
    }

}
