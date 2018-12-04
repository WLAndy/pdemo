package com.betazf.open.ali;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cloudauth.model.v20180807.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.betazf.utils.Base64Img;
import com.betazf.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClouDauthUtils {

    public static final String regionId = "cn-hangzhou";


    /**
     * RPBasic认证方案
     *
     * @param accessKeyId
     * @param accessKeySecre
     * @param biz
     */
    public static void rpBasic(String accessKeyId, String accessKeySecre, String biz) {
        //创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecre);
        IAcsClient client = new DefaultAcsClient(profile);
        String ticketId = UUID.randomUUID().toString(); //认证ID, 由使用方指定, 发起不同的认证任务需要更换不同的认证ID
        String token = null; //认证token, 表达一次认证会话
        int statusCode = -1; //-1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
        //1. 服务端发起认证请求, 获取到token
        GetVerifyTokenRequest getVerifyTokenRequest = new GetVerifyTokenRequest();
        getVerifyTokenRequest.setBiz(biz);
        getVerifyTokenRequest.setTicketId(ticketId);
        try {
            GetVerifyTokenResponse response = client.getAcsResponse(getVerifyTokenRequest);
            token = response.getData().getVerifyToken().getToken(); //token默认30分钟时效，每次发起认证时都必须实时获取
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //2. 服务端将token传递给无线客户端
        //3. 无线客户端用token调起无线认证SDK
        //4. 用户按照由无线认证SDK组织的认证流程页面的指引，提交认证资料
        //5. 认证流程结束退出无线认证SDK，进入客户端回调函数
        //6. 服务端查询认证状态(客户端回调中也会携带认证状态, 但建议以服务端调接口确认的为准)
        //GetStatus接口文档：https://help.aliyun.com/document_detail/57049.html
        GetStatusRequest getStatusRequest = new GetStatusRequest();
        getStatusRequest.setBiz(biz);
        getStatusRequest.setTicketId(ticketId);
        try {
            GetStatusResponse response = client.getAcsResponse(getStatusRequest);
            statusCode = response.getData().getStatusCode();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //7. 服务端获取认证资料
        GetMaterialsRequest getMaterialsRequest = new GetMaterialsRequest();
        getMaterialsRequest.setBiz(biz);
        getMaterialsRequest.setTicketId(ticketId);
        if (1 == statusCode || 2 == statusCode) { //认证通过or认证不通过
            try {
                GetMaterialsResponse response = client.getAcsResponse(getMaterialsRequest);
                //后续业务处理
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * RPBioID认证方案
     * @param accessKeyId
     * @param accessKeySecre
     * @param biz
     * @param bindingJson
     */
    public static void rpBioId(String accessKeyId, String accessKeySecre, String biz, String bindingJson) {
        //创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecre);
        IAcsClient client = new DefaultAcsClient(profile);
        String ticketId = UUID.randomUUID().toString(); //认证ID, 由使用方指定, 发起不同的认证任务需要更换不同的认证ID
        String token = null; //认证token, 表达一次认证会话
        int statusCode = -1; //-1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
        //1. 服务端发起认证请求, 获取到token
        //GetVerifyToken接口文档：https://help.aliyun.com/document_detail/57050.html
        GetVerifyTokenRequest getVerifyTokenRequest = new GetVerifyTokenRequest();
        getVerifyTokenRequest.setBiz(biz);
        getVerifyTokenRequest.setTicketId(ticketId);
        //若需要binding图片(如身份证正反面等), 且使用base64上传, 需要设置请求方法为POST
        //getVerifyTokenRequest.setMethod(MethodType.POST);
        //通过binding参数传入业务已经采集的认证资料，其中姓名、身份证号为必要字段
        //若需要binding图片资料，请控制单张图片大小在 2M 内，避免拉取超时
        getVerifyTokenRequest.setBinding(bindingJson);
        try {
            GetVerifyTokenResponse response = client.getAcsResponse(getVerifyTokenRequest);
            token = response.getData().getVerifyToken().getToken(); //token默认30分钟时效，每次发起认证时都必须实时获取
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //2. 服务端将token传递给无线客户端
        //3. 无线客户端用token调起无线认证SDK
        //4. 用户按照由无线认证SDK组织的认证流程页面的指引，提交认证资料
        //5. 认证流程结束退出无线认证SDK，进入客户端回调函数
        //6. 服务端查询认证状态(客户端回调中也会携带认证状态, 但建议以服务端调接口确认的为准)
        //GetStatus接口文档：https://help.aliyun.com/document_detail/57049.html
        GetStatusRequest getStatusRequest = new GetStatusRequest();
        getStatusRequest.setBiz(biz);
        getStatusRequest.setTicketId(ticketId);
        try {
            GetStatusResponse response = client.getAcsResponse(getStatusRequest);
            statusCode = response.getData().getStatusCode();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //7. 服务端获取认证资料
        //GetMaterials接口文档：https://help.aliyun.com/document_detail/57641.html
        GetMaterialsRequest getMaterialsRequest = new GetMaterialsRequest();
        getMaterialsRequest.setBiz(biz);
        getMaterialsRequest.setTicketId(ticketId);
        if (1 == statusCode || 2 == statusCode) { //认证通过or认证不通过
            try {
                GetMaterialsResponse response = client.getAcsResponse(getMaterialsRequest);
                //后续业务处理
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * RPH5BioOnly认证方案
     * @param accessKeyId
     * @param accessKeySecre
     * @param biz
     * @param bindingJson
     */
    public static void rpH5BioOnly(String accessKeyId, String accessKeySecre, String biz, String bindingJson) {
        //创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecre);
        IAcsClient client = new DefaultAcsClient(profile);
        String ticketId = UUID.randomUUID().toString(); //认证ID, 由使用方指定, 发起不同的认证任务需要更换不同的认证ID
        String token = null; //认证token, 表达一次认证会话
        int statusCode = -1; //-1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
        //1. 服务端发起认证请求, 获取到token
        //GetVerifyToken接口文档：https://help.aliyun.com/document_detail/57050.html
        GetVerifyTokenRequest getVerifyTokenRequest = new GetVerifyTokenRequest();
        getVerifyTokenRequest.setBiz(biz);
        getVerifyTokenRequest.setTicketId(ticketId);
        //若需要binding图片(如身份证正反面等), 且使用base64上传, 需要设置请求方法为POST
        //getVerifyTokenRequest.setMethod(MethodType.POST);
        //通过binding参数传入业务已经采集的认证资料，其中姓名、身份证号为必要字段
        //若需要binding图片资料，请控制单张图片大小在 2M 内，避免拉取超时
        getVerifyTokenRequest.setBinding(bindingJson);
        try {
            GetVerifyTokenResponse response = client.getAcsResponse(getVerifyTokenRequest);
            token = response.getData().getVerifyToken().getToken(); //token默认30分钟时效，每次发起认证时都必须实时获取
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //2. 服务端将认证URL(带token)传递给H5前端
        //3. H5前端跳转认证URL
        //4. 用户按照认证H5流程页面的指引，提交认证资料
        //5. 认证流程结束跳转指定的重定向URL
        //6. 服务端查询认证状态(建议以服务端调接口确认的为准)
        //GetStatus接口文档：https://help.aliyun.com/document_detail/57049.html
        GetStatusRequest getStatusRequest = new GetStatusRequest();
        getStatusRequest.setBiz(biz);
        getStatusRequest.setTicketId(ticketId);
        try {
            GetStatusResponse response = client.getAcsResponse(getStatusRequest);
            statusCode = response.getData().getStatusCode();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        //7. 服务端获取认证资料
        //GetMaterials接口文档：https://help.aliyun.com/document_detail/57641.html
        GetMaterialsRequest getMaterialsRequest = new GetMaterialsRequest();
        getMaterialsRequest.setBiz(biz);
        getMaterialsRequest.setTicketId(ticketId);
        if (1 == statusCode || 2 == statusCode) { //认证通过or认证不通过
            try {
                GetMaterialsResponse response = client.getAcsResponse(getMaterialsRequest);
                //后续业务处理
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * RPMin认证方案
     * @param accessKeyId
     * @param accessKeySecre
     * @param biz
     * @param idCardNumber
     * @param uName
     * @param idCardFrontPicUrl
     * @param idCardBackPicUrl
     */
    public static void rpMin(String accessKeyId, String accessKeySecre, String biz,String idCardNumber,String uName,String idCardFrontPicUrl,String idCardBackPicUrl){
        //创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecre);
        IAcsClient client = new DefaultAcsClient(profile);
        String ticketId = UUID.randomUUID().toString(); //认证ID, 由使用方指定, 发起不同的认证任务需要更换不同的认证ID
        String token = null; //认证token, 表达一次认证会话
        //1. 发起认证请求, 获取到token
        //GetVerifyToken接口文档：https://help.aliyun.com/document_detail/57050.html
        GetVerifyTokenRequest getVerifyTokenRequest = new GetVerifyTokenRequest();
        getVerifyTokenRequest.setBiz(biz); //传入采用RPMin认证方案的认证场景标识(biz)
        getVerifyTokenRequest.setTicketId(ticketId);
        try {
            GetVerifyTokenResponse response = client.getAcsResponse(getVerifyTokenRequest);
            token = response.getData().getVerifyToken().getToken(); //token默认30分钟时效，每次发起认证时都必须实时获取
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2. 用token提交认证材料
        //SubmitMaterials接口文档：https://help.aliyun.com/document_detail/58176.html
        SubmitMaterialsRequest submitRequest = new SubmitMaterialsRequest();
        submitRequest.setVerifyToken(token);
        //若使用base64提交图片, 需要设置请求方法为POST
        submitRequest.setMethod(MethodType.POST);
        //创建要提交的认证材料列表, 请根据 认证方案 中的说明传入相应字段
        List<SubmitMaterialsRequest.Material> verifyMaterials = new ArrayList<SubmitMaterialsRequest.Material>();
        SubmitMaterialsRequest.Material identificationNumber = new SubmitMaterialsRequest.Material();
        identificationNumber.setMaterialType("IdentificationNumber");
        identificationNumber.setValue(idCardNumber);
        verifyMaterials.add(identificationNumber);
        SubmitMaterialsRequest.Material name = new SubmitMaterialsRequest.Material();
        name.setMaterialType("Name");
        name.setValue(uName);
        verifyMaterials.add(name);
        //传入图片资料，请控制单张图片大小在 2M 内，避免拉取超时
        SubmitMaterialsRequest.Material facePic = new SubmitMaterialsRequest.Material();
        facePic.setMaterialType("FacePic");
        facePic.setValue("base64://iVBORw0KGgoA..."); //base64方式上传图片, 格式为"base64://图片base64字符串", 以"base64://"开头且图片base64字符串去掉头部描述(如"data:image/png;base64,"), 并注意控制接口请求的Body在8M以内
        verifyMaterials.add(facePic);
        SubmitMaterialsRequest.Material idCardFrontPic = new SubmitMaterialsRequest.Material();
        idCardFrontPic.setMaterialType("IdCardFrontPic");
        idCardFrontPic.setValue(idCardFrontPicUrl); //http方式上传图片, 此http地址须可公网访问
        verifyMaterials.add(idCardFrontPic);
        SubmitMaterialsRequest.Material idCardBackPic = new SubmitMaterialsRequest.Material();
        idCardBackPic.setMaterialType("IdCardBackPic");
        idCardBackPic.setValue(idCardBackPicUrl); //oss方式上传图片, 此oss文件地址须可公开访问
        verifyMaterials.add(idCardBackPic);
        submitRequest.setMaterials(verifyMaterials);
        try {
            SubmitMaterialsResponse response = client.getAcsResponse(submitRequest);
            //由于审核需要时间，SubmitMaterials接口并不保证同步返回认证结果，可能会返回认证中状态, 此时需要使用GetStatus接口轮询认证结果。
            //GetStatus接口文档：https://help.aliyun.com/document_detail/57049.html
            //GetStatusRequest getStatusRequest = new GetStatusRequest();
            //getStatusRequest.setBiz(biz);
            //getStatusRequest.setTicketId(ticketId);
            //GetStatusResponse response = client.getAcsResponse(getStatusRequest);
            //int statusCode = response.getData().getStatusCode();
            //后续业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void face(String accessKeyId, String accessKeySecre,String facePicUrl){
        //创建DefaultAcsClient实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecre);
        IAcsClient client = new DefaultAcsClient(profile);
        //创建API请求并设置参数
        //CompareFaces接口文档：https://help.aliyun.com/document_detail/59317.html
        CompareFacesRequest request = new CompareFacesRequest();
        //若使用base64上传图片, 需要设置请求方法为POST
        request.setMethod(MethodType.POST);
        //传入图片资料，请控制单张图片大小在 2M 内，避免拉取超时
        request.setSourceImageType("FacePic");
        request.setSourceImageValue(getBase64Pic(facePicUrl)); //base64方式上传图片, 格式为"base64://图片base64字符串", 以"base64://"开头且图片base64字符串去掉头部描述(如"data:image/png;base64,"), 并注意控制接口请求的Body在8M以内
        request.setTargetImageType("FacePic"); //若为身份证芯片照则传"IDPic"
        request.setTargetImageValue(facePicUrl); //http方式上传图片, 此http地址须可公网访问
        //发起请求并处理异常
        try {
            CompareFacesResponse response = client.getAcsResponse(request);
            //后续业务处理
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


    /**
     * 把图片转成base64并截取头信息
     * @param facePicUrl
     * @return
     */
    public static String getBase64Pic(String facePicUrl){
        //todo
        return null;
    }


    public static void main(String args[]){
        String host = "http://bea.xiaohuaerai.com";
        String path = "/bea";
        String method = "POST";
        String appcode = "94142c5490c54ee4b2a244712461f3d0";
        HashMap<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
//        String s = Base64Img.GetImageStrFromPath("/Users/andy/Desktop/WechatIMG144.jpeg");
//        String s = Base64Img.GetImageStrFromPath("/Users/andy/Desktop/WechatIMG145.jpeg");
        String s = Base64Img.GetImageStrFromPath("/Users/andy/Desktop/WechatIMG99.jpeg");
        bodys.put("src", s);
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            Map<String,Object> result = new HashMap<>();
            result.put("msg",unicodeToString((String) jsonObject.get("msg")));
            result.put("status", jsonObject.get("status"));
            result.put("accuracy", jsonObject.get("accuracy"));
            System.err.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }


    }
