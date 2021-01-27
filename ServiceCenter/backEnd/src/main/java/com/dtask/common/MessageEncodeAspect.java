package com.dtask.common;

import com.dtask.common.util.MessageEncoder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2020-7-2.
 */
@Component
@Aspect
public class MessageEncodeAspect {
    @Value("${secretKey}")
    private String secretKey;

    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public void messagePointCut() {}

    @Around("messagePointCut()")
    public Object encryptMsg(ProceedingJoinPoint pjp){
        MessageEncoder messageEncoder = new MessageEncoder(secretKey);

        try {
            Object[] objects = pjp.getArgs();

            if(objects.length == 1) {
                // 解密数据
                objects[0] = messageEncoder.decrypt(objects[0].toString());
            }

            // 执行
            String result = pjp.proceed(objects).toString();

            // 加密返回
            return messageEncoder.encrypt(result);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
