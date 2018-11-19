package com.zy.manage.im.http;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ImHttpHelp {

    public static final Logger logger = LoggerFactory.getLogger(ImHttpHelp.class);

    /**
     * 向云Im发送post请求
     * @param url
     * @param headerParams
     * @param bodyParams
     * @return Json字符串
     */
    public static String doPost(String url,Map<String,String> headerParams,Map<String,String> bodyParams){
        logger.debug("向云Im发送post请求:url {},headerParams {},bodyParams {}",url,headerParams.toString(),bodyParams.toString());
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headerParams.forEach((k,v) -> httpPost.addHeader(k,v) );
        List<NameValuePair> nameValuePairArrayList = new ArrayList<NameValuePair>();
        bodyParams.forEach((k,v) -> {
            if (!StringUtils.isEmpty(v))
                nameValuePairArrayList.add(new BasicNameValuePair(k, v));
        });
        HttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairArrayList, "utf-8"));
            response = httpClient.execute(httpPost);
            String result  = EntityUtils.toString(response.getEntity(), "utf-8");
            logger.debug("向云Im发送post请求 result:{}",result);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("请求发送失败:url {},headerParams {},bodyParams {}",url,headerParams.toString(),bodyParams.toString());
        return null;
    }
}
