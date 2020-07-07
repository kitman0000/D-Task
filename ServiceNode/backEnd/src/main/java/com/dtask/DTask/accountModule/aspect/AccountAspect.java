package com.dtask.DTask.accountModule.aspect;

import com.dtask.common.UserCommon;
import com.dtask.common.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2020-7-2.
 */
@Component
@Aspect
public class AccountAspect {
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
