package com.MQClouder;

import com.util.AESUtil;
import com.util.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhong on 2021-1-22.
 */
public class MessageEncoder {

    Logger logger = LoggerFactory.getLogger(MessageEncoder.class);

    public MessageEncoder(String secretKey) {
        this.secretKey = secretKey;
    }

    private String secretKey;

    public String decrypt(String msg){
        byte[] bytes = EncodeUtil.decodeBase64ToByte(msg);

        if(bytes == null)
            return null;

        try {
            return new String(AESUtil.decryptAES(bytes, secretKey),"utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("环境不支持utf-8编码");
            throw new RuntimeException();
        }
    }

    public String encrypt(String msg){
        byte[] encryptAES = AESUtil.encryptAES(msg, secretKey);
        return EncodeUtil.encodeBase64(encryptAES);
    }
}
