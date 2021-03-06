package com.zy.message;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MessageUtil {

    private static Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    private static final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    private static final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

    /**
     * 发送短息
     *
     * @param messageConf
     * @param phoneNumbers
     * @param signName
     * @param templateCode
     * @param templateParam
     * @param smsUpExtendCode
     * @param outId
     * @return
     */
    public static Map<String, String> sendSms(MessageConf messageConf, String phoneNumbers, String signName, String templateCode, String templateParam, String smsUpExtendCode, String outId) {
        SendSmsResponse sendSmsResponse = null;
        Map<String, String> result = new HashMap<String, String>();
        result.put("code", "FAIL");
        result.put("message", "请求异常");
        result.put("bizId", null);
        result.put("requestId", null);
        logger.info("发送短息-------> phoneNumbers{},signName{},templateCode{}",phoneNumbers,signName,templateCode);
        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", messageConf.getAccessKeyId(), messageConf.getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(templateParam);
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(outId);
            //请求失败这里会抛ClientException异常
            sendSmsResponse = acsClient.getAcsResponse(request);
            result.put("code", sendSmsResponse.getCode());
            result.put("message", sendSmsResponse.getMessage());
            result.put("bizId", sendSmsResponse.getBizId());
            result.put("requestId", sendSmsResponse.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送短息
     *
     * @param messageConf
     * @param phoneNumbers
     * @param signName
     * @param templateCode
     * @param templateParam
     * @return
     */
    public static Map<String, String> sendSms(MessageConf messageConf, String phoneNumbers, String signName, String templateCode, String templateParam) {
        return sendSms(messageConf, phoneNumbers, signName, templateCode, templateParam, "", "");
    }
}
