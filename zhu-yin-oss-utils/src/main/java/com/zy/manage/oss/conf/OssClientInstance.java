package com.zy.manage.oss.conf;


import com.aliyun.oss.OSSClient;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class OssClientInstance {

    public static OSSClient getClient(OssConfigParams ossConfigParams){
        return new OSSClient(ossConfigParams.getPrefix()+ossConfigParams.getEndpoint(),ossConfigParams.getAccessKeyId(),ossConfigParams.getAccessKeySecret());
    }

    public static OSSClient getClient(OssStsConfigParams ossStsConfigParams,Map<String,String> stsMap){
        String code = stsMap.get("code");
        if (!code.equals("200")){
            return null;
        }
        String securityToken = stsMap.get("securityToken");
        String accessIdSecret = stsMap.get("accessIdSecret");
        String accessId = stsMap.get("accessId");
        if (StringUtils.isEmpty(accessId) || StringUtils.isEmpty(accessIdSecret) || StringUtils.isEmpty(securityToken)){
            return null;
        }
        return new OSSClient(ossStsConfigParams.getPrefix()+ossStsConfigParams.getEndpoint(),accessId,accessIdSecret,securityToken);
    }
}
