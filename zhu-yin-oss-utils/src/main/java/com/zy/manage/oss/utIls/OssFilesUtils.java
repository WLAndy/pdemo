package com.zy.manage.oss.utIls;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;

import java.util.ArrayList;
import java.util.List;

public class OssFilesUtils {

    private OSSClient ossClient;

    public OssFilesUtils(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    /**
     * Object是否存在
     *
     * @param bucketName
     * @param key
     * @return
     */
    public boolean doesObjectExist(String bucketName, String key) {
        return ossClient.doesObjectExist(bucketName, key);
    }


    /**
     * 设置Object ACL
     *
     * @param bucketName
     * @param key
     * @param value
     * @return
     */
    private boolean setObjectAcl(String bucketName, String key, CannedAccessControlList value) {
        boolean flag = false;
        try {
            ossClient.setObjectAcl(bucketName, key, value);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    /**
     * 设置Object ACL(默认)
     *
     * @param bucketName
     * @param key
     * @return
     */
    public boolean setObjectAclDefault(String bucketName, String key) {
        return setObjectAcl(bucketName, key, CannedAccessControlList.Default);
    }

    /**
     * 设置Object ACL(私有)
     *
     * @param bucketName
     * @param key
     * @return
     */
    public boolean setObjectAclPrivate(String bucketName, String key) {
        return setObjectAcl(bucketName, key, CannedAccessControlList.Private);
    }

    /**
     * 设置Object ACL(公读私写)
     *
     * @param bucketName
     * @param key
     * @return
     */
    public boolean setObjectAclPublicRead(String bucketName, String key) {
        return setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
    }

    /**
     * 设置Object ACL(公共读写)
     * 小心使用
     *
     * @param bucketName
     * @param key
     * @return
     */
    @Deprecated
    public boolean setObjectAclPublicReadWrite(String bucketName, String key) {
        return setObjectAcl(bucketName, key, CannedAccessControlList.PublicReadWrite);
    }


    /**
     * 获取文件全部元信息(Object Meta) .
     *
     * @param bucketName
     * @param key
     * @return
     */
    public ObjectMetadata getObjectMetadata(String bucketName, String key) {
        return ossClient.getObjectMetadata(bucketName, key);
    }

    /**
     * 最多只能返回100条Object
     *
     * @param bucketName
     * @return
     */
    public ObjectListing listObjects(String bucketName) {
        return ossClient.listObjects(bucketName);
    }

    /**
     * 最多只能返回100条Object
     *
     * @param bucketName
     * @param prefix     本次查询结果的开始前缀
     * @return
     */
    public ObjectListing listObjects(String bucketName, String prefix) {
        return ossClient.listObjects(bucketName, prefix);
    }

    /**
     * 多条件列举
     *
     * @param listObjectsRequest
     * @return
     */
    public ObjectListing objectListing(ListObjectsRequest listObjectsRequest) {
        return ossClient.listObjects(listObjectsRequest);
    }

    /**
     * 单一删除
     * @param bucketName
     * @param key
     * @return
     */
    public boolean deleteObject(String bucketName, String key) {
        boolean flag = false;
        try {
            ossClient.deleteObject(bucketName, key);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    /**
     * 删除多个文件
     * @param bucketName
     * @param keys
     * @return 返回的是删除成功的key
     */
    public List<String> deleteObjects(String bucketName,List<String> keys){
        DeleteObjectsResult  deletedObjects = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys).withQuiet(false));
        return deletedObjects.getDeletedObjects();
    }

    /**
     * 简单拷贝
     * @param srcBucketName 原bucketName
     * @param srcKey 原Key
     * @param destBucketName 新bucketName
     * @param destKey 新Key
     * 用户需要有源Object的操作权限，否则会无法完成操作。
     * 该操作不支持跨Region拷贝数据。比如：不支持将杭州Bucket里的Object拷贝到青岛。
     * 该操作支持的最大Object大小为1GB。
     */
    public boolean copyObject(String srcBucketName,String srcKey,String destBucketName,String destKey){
        boolean flag = false;
        try {
            ossClient.copyObject(srcBucketName,srcKey,destBucketName,destKey);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return flag;
        }
    }

    /**
     * 大文件拷贝
     * @param sourceBucketName
     * @param sourceKey
     * @param targetBucketName
     * @param targetKey
     * @return
     * 用户需要有源Object的操作权限，否则会无法完成操作。
     * 该操作不支持跨Region拷贝数据。比如：不支持将杭州Bucket里的Object拷贝到青岛。
     * 该操作支持的最大Object大小为1GB。
     */
    public boolean copyObjectToBig(String sourceBucketName,String sourceKey,String targetBucketName,String targetKey){
        boolean flag = false;
        // 得到被拷贝object大小
        ObjectMetadata objectMetadata = ossClient.getObjectMetadata(sourceBucketName, sourceKey);
        long contentLength = objectMetadata.getContentLength();
        // 分片大小，10MB
        long partSize = 1024 * 1024 * 10;
        // 计算分块数目
        int partCount = (int) (contentLength / partSize);
        if (contentLength % partSize != 0) {
            partCount++;
        }
        System.out.println("total part count:" + partCount);
        // 初始化拷贝任务
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(targetBucketName, targetKey);
        InitiateMultipartUploadResult initiateMultipartUploadResult = ossClient.initiateMultipartUpload(initiateMultipartUploadRequest);
        String uploadId = initiateMultipartUploadResult.getUploadId();
        try {
            // 分片拷贝
            List<PartETag> partETags = new ArrayList<PartETag>();
            for (int i = 0; i < partCount; i++) {
                // 计算每个分块的大小
                long skipBytes = partSize * i;
                long size = partSize < contentLength - skipBytes ? partSize : contentLength - skipBytes;
                // 创建UploadPartCopyRequest
                UploadPartCopyRequest uploadPartCopyRequest = new UploadPartCopyRequest(sourceBucketName, sourceKey, targetBucketName, targetKey);
                uploadPartCopyRequest.setUploadId(uploadId);
                uploadPartCopyRequest.setPartSize(size);
                uploadPartCopyRequest.setBeginIndex(skipBytes);
                uploadPartCopyRequest.setPartNumber(i + 1);
                UploadPartCopyResult uploadPartCopyResult = ossClient.uploadPartCopy(uploadPartCopyRequest);
                // 将返回的PartETag保存到List中
                partETags.add(uploadPartCopyResult.getPartETag());
            }
            // 提交分片拷贝任务
            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                    targetBucketName, targetKey, uploadId, partETags);
            ossClient.completeMultipartUpload(completeMultipartUploadRequest);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return flag;
        }
    }



}
