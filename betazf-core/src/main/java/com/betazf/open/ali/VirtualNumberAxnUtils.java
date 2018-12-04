package com.betazf.open.ali;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyplsapi.model.v20170525.BindAxnRequest;
import com.aliyuncs.dyplsapi.model.v20170525.BindAxnResponse;
import com.aliyuncs.dyplsapi.model.v20170525.UnbindSubscriptionRequest;
import com.aliyuncs.dyplsapi.model.v20170525.UnbindSubscriptionResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class VirtualNumberAxnUtils {

    //产品名称:云通信隐私保护产品,开发者无需替换
    static final String product = "Dyplsapi";
    //产品域名,开发者无需替换
    static final String domain = "dyplsapi.aliyuncs.com";

    /**
     * AXN绑定示例
     *
     * @return
     * @throws ClientException
     */
    public static BindAxnResponse bindAxn(String phoneNoA, String phoneNoB, String expiration, String accessKeyId, String accessKeySecret, String poolKey) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        BindAxnRequest request = new BindAxnRequest();
        //必填:对应的号池Key
        request.setPoolKey(poolKey);
        //必填:AXN关系中的A号码
        request.setPhoneNoA(phoneNoA);
        if (null != phoneNoB)
            //可选:AXN中A拨打X的时候转接到的默认的B号码,如果不需要则不设置
            request.setPhoneNoB(phoneNoB);
        //必填:绑定关系对应的失效时间-不能早于当前系统时间 2017-09-08 17:00:00
        request.setExpiration(expiration);
        //可选:是否需要录制音频-默认是false
        request.setIsRecordingEnabled(false);
        //外部业务自定义ID属性
        //request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        BindAxnResponse response = acsClient.getAcsResponse(request);
        return response;
    }

    /**
     * 订购关系解绑示例(解绑接口不区分AXB、AXN)
     *
     * @return
     * @throws ClientException
     */
    public static UnbindSubscriptionResponse unbind(String subsId, String secretNo, String accessKeyId, String accessKeySecret, String poolKey) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        UnbindSubscriptionRequest request = new UnbindSubscriptionRequest();
        //必填:对应的号池Key
        request.setPoolKey(poolKey);
        //必填-分配的X小号-对应到绑定接口中返回的secretNo;
        request.setSecretNo(secretNo);
        //可选-绑定关系对应的ID-对应到绑定接口中返回的subsId;
        request.setSubsId(subsId);

        UnbindSubscriptionResponse response = acsClient.getAcsResponse(request);

        return response;
    }
}
