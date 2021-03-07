package com.MQClouder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Created by zhong on 2020-7-2.
 */
@Component
@Aspect
public class MessageEncodeAspect {
    @Value("${secretKey}")
    private String secretKey;

    @Pointcut("@annotation(com.MQClouder.MessageReceiver)")
    public void messagePointCut() {}

    @Around("messagePointCut()")
    public Object encryptMsg(ProceedingJoinPoint pjp){
        MessageEncoder messageEncoder = new MessageEncoder(secretKey);

        try {
            Object[] objects = pjp.getArgs();

            String result = "";

            if(objects.length == 1 && objects[0] != null) {
                // 解密数据
                objects[0] = messageEncoder.decrypt(objects[0].toString());
                // 执行
                result = pjp.proceed(objects).toString();
            }else {
                // 没有参数，直接执行
                result = pjp.proceed().toString();
            }

            if (result == null){
                return null;
            }

            // 加密返回
            return messageEncoder.encrypt(result);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
