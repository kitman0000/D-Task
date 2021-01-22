package com.dtask.common.util;

import com.dtask.common.config.RabbitSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2021-1-11.
 */
@Component
public class EncryptRabbitSender extends RabbitSender {
    private Logger logger = LoggerFactory.getLogger(EncryptRabbitSender.class);

    @Value("${secretKey}")
    private String secretKey;

    public String encryptSend(String routingKey,String msg){
        byte[] encryptAES = AESUtil.encryptAES(msg, secretKey);
        String result = send(routingKey, EncodeUtil.encodeBase64(encryptAES));
        byte[] resultByte = EncodeUtil.decodeBase64ToByte(result);
        return new String(AESUtil.decryptAES(resultByte,secretKey));
    }

    public void encryptSendWithoutResponse(String routingKey,String msg) {
        byte[] encryptAES = AESUtil.encryptAES(msg, secretKey);
        sendWithoutResponse(routingKey,EncodeUtil.encodeBase64(encryptAES));
    }
}
