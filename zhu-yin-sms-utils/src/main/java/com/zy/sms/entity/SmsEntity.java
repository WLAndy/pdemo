package com.zy.sms.entity;

import java.io.Serializable;

public class SmsEntity implements Serializable{

    private String batchName;

    private String content;


    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SmsEntity(String batchName, String content) {
        this.batchName = batchName;
        this.content = content;
    }

    public SmsEntity() {
    }
}
