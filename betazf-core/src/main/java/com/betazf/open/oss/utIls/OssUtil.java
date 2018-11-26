package com.betazf.open.oss.utIls;


import com.aliyun.oss.OSSClient;

public class OssUtil {

    public OSSClient ossClient;

    public OssBucketUtils ossBucketUtils;

    public OssDownloadUtils ossDownloadUtils;

    public OssUploadUtils ossUploadUtils;

    public OssUtil(OSSClient ossClient) {
        this.ossClient = ossClient;
        this.ossBucketUtils = new OssBucketUtils(ossClient);
        this.ossDownloadUtils = new OssDownloadUtils(ossClient);
        this.ossUploadUtils = new OssUploadUtils(ossClient);
    }




}
