package com.dtask.common.util;

/**
 * Created by zhong on 2021-1-22.
 */
public class MessageEncoder {

    public MessageEncoder(String secretKey) {
        this.secretKey = secretKey;
    }

    private String secretKey;

    public String decrypt(String msg){
        byte[] bytes = EncodeUtil.decodeBase64ToByte(msg);
        return new String(AESUtil.decryptAES(bytes, secretKey));
    }

    public String encrypt(String msg){
        byte[] encryptAES = AESUtil.encryptAES(msg, secretKey);
        return EncodeUtil.encodeBase64(encryptAES);
    }
}
