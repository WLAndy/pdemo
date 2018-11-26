package com.betazf.open.oss.utIls;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.UploadFileRequest;

import java.io.*;

public class OssUploadUtils {

    private OSSClient ossClient;

    public OssUploadUtils(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    /**
     * 上传字符串
     *
     * @param bucketName
     * @param key
     * @param value
     * @return
     */
    public String putString(String bucketName, String key, String value) {
        try {
            putByteArray(bucketName, key, value.getBytes());
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传byte数组
     *
     * @param bucketName
     * @param key
     * @param bytes
     * @return
     */
    public String putByteArray(String bucketName, String key, byte[] bytes) {
        try {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(bytes));
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传网络流
     *
     * @param bucketName
     * @param key
     * @param inputStream
     * @return
     */
    public String putInputStream(String bucketName, String key, InputStream inputStream) {
        try {
            ossClient.putObject(bucketName, key, inputStream);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传文件流
     *
     * @param bucketName
     * @param key
     * @param fileInputStream
     * @return
     */
    public String putFileInputStream(String bucketName, String key, FileInputStream fileInputStream) {
        try {
            ossClient.putObject(bucketName, key, fileInputStream);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传本地文件
     *
     * @param bucketName
     * @param key
     * @param file
     * @return
     */
    public String putFile(String bucketName, String key, File file) {
        try {
            ossClient.putObject(bucketName, key, file);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 断点续传上传
     *
     * @param bucketName
     * @param key
     * @param localFilePath
     * @return
     */
    public String uploadFile(String bucketName, String key, String localFilePath) {
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
        uploadFileRequest.setUploadFile(localFilePath);
        // 指定上传并发线程数
        uploadFileRequest.setTaskNum(5);
        // 指定上传的分片大小
        uploadFileRequest.setPartSize(1 * 1024 * 1024);
        // 开启断点续传
        uploadFileRequest.setEnableCheckpoint(true);
        try {
            // 断点续传上传
            ossClient.uploadFile(uploadFileRequest);
            return key;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


}
