package com.MQClouder;

import com.util.AESUtil;
import com.util.EncodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by zhong on 2021-1-11.
 */
@Component
public class EncryptRabbitSender extends RabbitSender {
    private Logger logger = LoggerFactory.getLogger(EncryptRabbitSender.class);

    @Value("${secretKey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    public String encryptSend(String routingKey,String msg){
        MessageEncoder messageEncoder = new MessageEncoder(secretKey);
        String encryptStr = messageEncoder.encrypt(msg);

        String result = send(routingKey, encryptStr);
        return messageEncoder.decrypt(result);
    }

    public void encryptSendWithoutResponse(String routingKey,String msg) {
        byte[] encryptAES = AESUtil.encryptAES(msg, secretKey);
        sendWithoutResponse(routingKey,EncodeUtil.encodeBase64(encryptAES));
    }
}
