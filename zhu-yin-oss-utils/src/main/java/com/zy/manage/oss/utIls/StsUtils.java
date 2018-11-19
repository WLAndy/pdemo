package com.zy.manage.oss.utIls;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zy.manage.im.conf.OssStsConfigParams;

import java.util.HashMap;
import java.util.Map;

public class StsUtils {
    /**
     * 获取零时授权
     * @param ossStsConfigParams
     * @return
     */
    public static Map<String,String> getStsToken(OssStsConfigParams ossStsConfigParams){
        Map<String,String> result = new HashMap<String, String>();
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"im:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:im:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            // Init ACS Client
            DefaultProfile.addEndpoint("", "", "Sts", ossStsConfigParams.getStsendpoint());
            IClientProfile profile = DefaultProfile.getProfile("", ossStsConfigParams.getStsAccessKeyId(), ossStsConfigParams.getStsAccessKeySecret());
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(ossStsConfigParams.getRoleArn());
            request.setRoleSessionName(ossStsConfigParams.getRoleSessionName());
            request.setPolicy(policy); // Optional
            final AssumeRoleResponse response = client.getAcsResponse(request);
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
            result.put("accessId",response.getCredentials().getAccessKeyId());
            result.put("accessIdSecret",response.getCredentials().getAccessKeySecret());
            result.put("securityToken",response.getCredentials().getSecurityToken());
            result.put("code","200");
            result.put("message","success");
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
            result.put("code","500");
            result.put("message",e.getErrMsg());
        }
        return result;
    }
}
