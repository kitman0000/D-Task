package com.dtask.DTask.accountModule.aspect;

import com.dtask.common.UserCommon;
import com.dtask.common.util.AESUtil;
import com.dtask.common.util.EncodeUtil;
import com.dtask.common.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Created by zhong on 2020-7-2.
 */
@Component
@Aspect
public class AccountAspect {
    @Value("${secretKey}")
    private String secretKey;

    Logger logger = LoggerFactory.getLogger(AccountAspect.class);

    @Pointcut("execution(public * com.dtask.DTask.accountModule.controller.*.*(..))")
    public void accountPointCut() {}

    @Before("accountPointCut()")
    public void doBeforePointCut(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = JsonUtil.objectToJson(joinPoint.getArgs());

        try {
            int userID = UserCommon.getUserBo().getUserID();
            logger.info("用户ID:" + userID + "    执行:" + methodName + "   参数:" + args);
        }
        catch (Exception ex){
            // 如果是尚未登录的用户，无法获取userID，执行这里的代码
            logger.info("未登录用户执行:" + methodName + "   参数:" + args);
        }
    }

    // 需要检查的Url
    private static HashSet<String> mqUrl = new HashSet<>();
    static {
        mqUrl.add("mqRemoteLogin");
    }

    @Around("accountPointCut()")
    public Object encryptMsg(ProceedingJoinPoint pjp){
        try {
            if(!mqUrl.contains(pjp.getSignature().getName())){
                return pjp.proceed();
            }

            Object[] objects = pjp.getArgs();

            if(objects.length == 1) {
                // 解密数据
                byte[] bytes = EncodeUtil.decodeBase64ToByte(objects[0].toString());
                objects[0] = new String(AESUtil.decryptAES(bytes, secretKey));
            }

            // 执行
            String result = pjp.proceed(objects).toString();

            // 加密返回
            byte[] encryptAES = AESUtil.encryptAES(result, secretKey);
            return EncodeUtil.encodeBase64(encryptAES);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }


    @AfterReturning(value = "accountPointCut()",returning = "keys")
    public void doAfterPointCut(JoinPoint joinPoint, Object keys){
        String methodName = joinPoint.getSignature().getName();
        String keysJson = JsonUtil.objectToJson(keys);
        try {
            int userID = UserCommon.getUserBo().getUserID();
            logger.info("用户ID:" + userID + "    执行:" + methodName + "   返回:" + keysJson);
        } catch (Exception ex){
            logger.info("未登录用户执行:" + methodName + "   返回:" + keysJson);
        }

    }
}
