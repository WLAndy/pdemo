package com.zy.manage.oss.utIls;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.zy.manage.oss.conf.OssConfigParams;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlSignUtils {


    /**
     * 获取url签名
     * @param ossConfigParams
     * @param bucket
     * @param dir
     * @param expireTime 秒
     * @return
     */
    public static Map<String,String> getUrlSign(OssConfigParams ossConfigParams, String bucket,String dir,long expireTime){
        String host = ossConfigParams.getPrefix() + bucket + "." +ossConfigParams.getEndpoint();
        OSSClient client = new OSSClient(ossConfigParams.getPrefix()+ossConfigParams.getEndpoint(), ossConfigParams.getAccessKeyId(), ossConfigParams.getAccessKeySecret());
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        respMap.put("code","500");
        respMap.put("message","系统异常");
        try {
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            respMap.put("accessid", ossConfigParams.getAccessKeyId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            respMap.put("code","200");
            respMap.put("message","请求成功");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return respMap;
    }

}
