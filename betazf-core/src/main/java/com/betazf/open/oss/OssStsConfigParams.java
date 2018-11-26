package com.betazf.open.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ali.oss")
public class OssStsConfigParams {

    private String prefix;

    private String endpoint;

    private String stsendpoint;

    private String stsAccessKeyId;

    private String stsAccessKeySecret;

    private String roleArn;

    private String roleSessionName;

    public OssStsConfigParams() {
    }

    public OssStsConfigParams(String prefix, String endpoint, String stsendpoint, String stsAccessKeyId, String stsAccessKeySecret, String roleArn, String roleSessionName) {
        this.prefix = prefix;
        this.endpoint = endpoint;
        this.stsendpoint = stsendpoint;
        this.stsAccessKeyId = stsAccessKeyId;
        this.stsAccessKeySecret = stsAccessKeySecret;
        this.roleArn = roleArn;
        this.roleSessionName = roleSessionName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getStsendpoint() {
        return stsendpoint;
    }

    public void setStsendpoint(String stsendpoint) {
        this.stsendpoint = stsendpoint;
    }

    public String getStsAccessKeyId() {
        return stsAccessKeyId;
    }

    public void setStsAccessKeyId(String stsAccessKeyId) {
        this.stsAccessKeyId = stsAccessKeyId;
    }

    public String getStsAccessKeySecret() {
        return stsAccessKeySecret;
    }

    public void setStsAccessKeySecret(String stsAccessKeySecret) {
        this.stsAccessKeySecret = stsAccessKeySecret;
    }

    public String getRoleArn() {
        return roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
    }

    public String getRoleSessionName() {
        return roleSessionName;
    }

    public void setRoleSessionName(String roleSessionName) {
        this.roleSessionName = roleSessionName;
    }
}
