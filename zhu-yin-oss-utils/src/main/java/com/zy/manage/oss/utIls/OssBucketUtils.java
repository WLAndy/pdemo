package com.zy.manage.oss.utIls;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;

import java.util.List;

public class OssBucketUtils {

    private OSSClient ossClient;

    public OssBucketUtils(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    /**
     * 创建Bucket
     * @param bucketName
     * @return
     */
    public Bucket createBucket(String bucketName) {
        return ossClient.createBucket(bucketName);
    }

    /**
     * 列举Bucket
     * @param ossClient
     * @return
     */
    public List<Bucket> listBuckets(OSSClient ossClient) {
        return ossClient.listBuckets();
    }

    /**
     * 指定前缀列举Bucket
     *
     * @param bucketPrefix
     * @return
     */
    public BucketList listBuckets( String bucketPrefix) {
        ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        listBucketsRequest.setPrefix(bucketPrefix);
        return ossClient.listBuckets(listBucketsRequest);
    }

    /**
     * 指定maxkeys列举Bucket
     * @param maxKey
     * @return
     */
    public BucketList listBuckets( int maxKey) {
        ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        listBucketsRequest.setMaxKeys(maxKey);
        return ossClient.listBuckets(listBucketsRequest);
    }

    /**
     * 删除Bucket
     * @param bucketName
     * @return
     */
    public boolean deleteBucket( String bucketName) {
        boolean flag = false;
        try {
            ossClient.deleteBucket(bucketName);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    /**
     * 判断Bucket是否存在
     * @param bucketName
     * @return
     */
    public boolean hasBucket( String bucketName) {
        return ossClient.doesBucketExist(bucketName);
    }

    /**
     * 设置Bucket ACL
     * @param bucketName
     * @param value
     * @return
     */
    private boolean setBucketAcl(String bucketName, CannedAccessControlList value) {
        boolean flag = false;
        try {
            ossClient.setBucketAcl(bucketName, value);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    /**
     * 设置Bucket ACL(默认)
     * @param bucketName
     * @return
     */
    public boolean setBucketAclDefaut(String bucketName) {
        return setBucketAcl(bucketName, CannedAccessControlList.Default);
    }

    /**
     * 设置Bucket ACL(私有)
     * @param bucketName
     * @return
     */
    public boolean setBucketAclPrivate(String bucketName) {
        return setBucketAcl(bucketName, CannedAccessControlList.Private);
    }

    /**
     * 设置Bucket ACL(公读私写)
     * @param bucketName
     * @return
     */
    public boolean setBucketAclPublicRead(String bucketName) {
        return setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    /**
     * 设置Bucket ACL(公共读写)
     * 小心使用
     * @param bucketName
     * @return
     */
    @Deprecated
    public boolean setBucketAclPublicReadWrite(String bucketName) {
        return setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
    }

    /**
     * 获取Bucket Location
     * @param bucketName
     * @return
     */
    public String getBucketLocation(String bucketName) {
        return ossClient.getBucketLocation(bucketName);
    }

    /**
     * 获取Bucket Info
     * @param bucketName
     * @return
     */
    public BucketInfo getBucketInfo(String bucketName) {
        return ossClient.getBucketInfo(bucketName);
    }
}
