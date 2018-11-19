package com.betazf.core;

import com.betazf.utils.encryption.AESEncoder;

import java.io.Serializable;

/**
 * 响应结果生成工具
 */
public class ResultGenerator implements Serializable{
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }



    public static String genSuccessResultToString() {
        return genSuccessResult().toString();
    }

    public static String genSuccessResultToString(Object data) {
        return genSuccessResult(data).toString();
    }

    public static String genFailResultToString(String message) {
        return genFailResult(message).toString();
    }



    public static String genSuccessResultByAes(String aesKey) {
        return AESEncoder.encrypt(genSuccessResult().toString(),aesKey);
    }

    public static String genSuccessResultByAes(Object data,String aesKey) {
        return AESEncoder.encrypt(genSuccessResult(data).toString(),aesKey);
    }

    public static String genFailResultByAes(String message,String aesKey) {
        return AESEncoder.encrypt(genFailResult(message).toString(),aesKey);
    }
}
