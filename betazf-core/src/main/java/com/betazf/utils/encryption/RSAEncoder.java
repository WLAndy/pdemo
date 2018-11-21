package com.betazf.utils.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncoder {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static final BASE64Encoder base64Encoder = new BASE64Encoder();

    private static final BASE64Decoder base64Decoder = new BASE64Decoder();

    /**
     * 对数据分段解密
     *
     * @param decryptionData
     * @param cipher
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String dataPiecewiseDecryption(String decryptionData, Cipher cipher) throws BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] encryptedDataByet = base64Decoder.decodeBuffer(decryptionData);
        int inputLen = encryptedDataByet.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedDataByet, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedDataByet, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }


    /**
     * 对数据分段加密
     *
     * @param encryptedData
     * @param cipher
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */
    public static String dataPiecewiseEncryption(String encryptedData, Cipher cipher) throws BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] encryptedDataByet = encryptedData.getBytes();
        int inputLen = encryptedDataByet.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedDataByet, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedDataByet, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return base64Encoder.encodeBuffer(decryptedData);
    }


    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom sr = new SecureRandom();
        keyPairGen.initialize(1024, sr);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        Map<String, Key> keyMap = new HashMap<String, Key>(2);
        keyMap.put("public_key", publicKey);
        keyMap.put("private_key", privateKey);
        return keyMap;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(String data, String privateKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(base64Decoder.decodeBuffer(data));
        return base64Encoder.encode(signature.sign());
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(String data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(base64Decoder.decodeBuffer(data));
        return signature.verify(base64Decoder.decodeBuffer(sign));
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);

        return dataPiecewiseDecryption(encryptedData, cipher);
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        return dataPiecewiseDecryption(encryptedData, cipher);
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String publicKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        return dataPiecewiseEncryption(data, cipher);
    }


    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        return dataPiecewiseEncryption(data, cipher);
    }


    /**
     * 得到公钥
     *
     * @param keyMap
     * @return
     */
    public static String getPublicKey(Map<String, Key> keyMap) {
        return new BASE64Encoder().encodeBuffer(keyMap.get("public_key").getEncoded());
    }

    /**
     * 得到私钥
     *
     * @param keyMap
     * @return
     */
    public static String getPrivateKey(Map<String, Key> keyMap) {
        return new BASE64Encoder().encodeBuffer(keyMap.get("private_key").getEncoded());
    }


    public static void main(String[] args) throws Exception {
        Map<String, Key> keyMap = RSAEncoder.genKeyPair();
        String publicKey = RSAEncoder.getPublicKey(keyMap);
        String privateKey = RSAEncoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);
//        System.err.println("公钥加密——私钥解密");
//        String source = "fadfdf";
//        System.out.println("\r加密前文字：\r\n" + source);
////
////
////        String encodedData = RSAEncoder.encryptByPublicKey(source, publicKey);
////        System.out.println("加密后文字：\r\n" + encodedData);
////        String decodedData = RSAEncoder.decryptByPrivateKey(encodedData, privateKey);
////        System.out.println("解密后文字: \r\n" + decodedData);
//////
////
////        String param = source;
////        String encodedData1 = RSAEncoder.encryptByPrivateKey(param, privateKey);
////        System.out.println("加密后：" + encodedData1);
////
////        String decodedData1 = RSAEncoder.decryptByPublicKey(encodedData1, publicKey);
////        System.out.println("解密后：" + new String(decodedData1));
////
////        String sign = RSAEncoder.sign(encodedData1, privateKey);
////        System.err.println("签名：" + sign);
////
////        boolean status = RSAEncoder.verify(encodedData1, publicKey, sign);
////        System.err.println("签名验证结果：" + status);
//
////        String pubData = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNMajrJOFf6GXn/dXK6ClUzn22T+CHoJfb2nJcpswvRUdse0b3d+i9YVk+KgaCCq1jS9jj4Ah3YsaBmlne1EKUO8SpMxEuLfWVVslmitE3XK2Gd7m2HyYXupHGTXYE32Aa36Y7B91n1T90gav94c10kx3j2VnuQO2IBEluBEU55QIDAQAB";
////        String selfPublicKey = RSAEncoder.encryptByPublicKey(pubData, publicKey);
////        System.err.println(selfPublicKey);
////        String decryptByPrivateKey = RSAEncoder.decryptByPrivateKey(selfPublicKey, privateKey);
////        System.err.println(decryptByPrivateKey);
//
    }

}
