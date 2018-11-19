package com.zy.manage.oss.utIls;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

import java.io.File;
import java.io.InputStream;

public class OssDownloadUtils {

    private OSSClient ossClient;

    public OssDownloadUtils(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    /**
     * 流式下载
     *
     * @param bucketName
     * @param key
     * @return
     */
    public InputStream downloadByInputStream(String bucketName, String key) {
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        if (ossClient == null)
            return null;
        return ossObject.getObjectContent();
    }


    /**
     * 下载到本地文件
     *
     * @param bucketName
     * @param key
     * @param loaclFilePath 传入路径加文件名即可 如：d://loacl/file.txt
     * @return
     */
    public String downloadForLocal(String bucketName, String key, String loaclFilePath) {
        try {
            ossClient.getObject(new GetObjectRequest(bucketName, key), new File(loaclFilePath));
            return loaclFilePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 断点续传下载
     *
     * @param bucketName
     * @param key
     * @param loaclFilePath
     * @return
     */
    public String downloadFileRequest(String bucketName, String key, String loaclFilePath) {
        // 下载请求，10个任务并发下载，启动断点续传
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key);
        downloadFileRequest.setDownloadFile(loaclFilePath);
        downloadFileRequest.setTaskNum(10);
        downloadFileRequest.setEnableCheckpoint(true);
        // 下载文件
        DownloadFileResult downloadRes = null;
        try {
            downloadRes = ossClient.downloadFile(downloadFileRequest);
            // 下载成功时，会返回文件的元信息
            downloadRes.getObjectMetadata();
            return loaclFilePath;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
