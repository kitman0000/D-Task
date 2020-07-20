package com.dtask.DTask.userModule.aspect;

import com.dtask.common.UserCommon;
import com.dtask.common.util.JsonUtil;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhong on 2020-7-2.
 */
@Component
@Aspect
public class UserAspect {
    Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @Pointcut("execution(public * com.dtask.DTask.userModule.controller.*.*(..))")
    public void userPointCut() {}

    @Before("userPointCut()")
    public void doBeforePointCut(JoinPoint joinPoint) {

        int userID = UserCommon.getUserBo().getUserID();
        String methodName = joinPoint.getSignature().getName();
        String args = JsonUtil.objectToJson(joinPoint.getArgs());

        // 不记录同步用户程序
        if(methodName.equals("syncUserInfo"))
            return;
        logger.info("用户ID:" + userID + "    执行:" + methodName + "   参数:" + args);
    }

    @AfterReturning(value = "userPointCut()",returning = "keys")
    public void doAfterPointCut(JoinPoint joinPoint, Object keys){
        int userID = UserCommon.getUserBo().getUserID();
        String methodName = joinPoint.getSignature().getName();
        String keysJson = JsonUtil.objectToJson(keys);

        // 不记录无数据的同步用户程序
        if(methodName.equals("syncUserInfo") && keysJson.toLowerCase().equals("false"))
            return;
        logger.info("用户ID:" + userID + "    执行:" + methodName + "   返回:" + keysJson);
    }
}
