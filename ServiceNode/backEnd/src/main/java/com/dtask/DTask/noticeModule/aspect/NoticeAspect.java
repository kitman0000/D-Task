package com.dtask.DTask.noticeModule.aspect;

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
public class NoticeAspect {
    Logger logger = LoggerFactory.getLogger(NoticeAspect.class);

    @Pointcut("execution(public * com.dtask.DTask.noticeModule.controller.*.*(..))")
    public void noticePointCut() {}

    @Before("noticePointCut()")
    public void doBeforePointCut(JoinPoint joinPoint) {
        int userID = UserCommon.getUserBo().getUserID();
        String methodName = joinPoint.getSignature().getName();
        String args = JsonUtil.objectToJson(joinPoint.getArgs());

        logger.info("用户ID:" + userID + "    执行:" + methodName + "   参数:" + args);
    }

    @AfterReturning(value = "noticePointCut()",returning = "keys")
    public void doAfterPointCut(JoinPoint joinPoint, Object keys){
        int userID = UserCommon.getUserBo().getUserID();
        String methodName = joinPoint.getSignature().getName();
        String keysJson = JsonUtil.objectToJson(keys);

        logger.info("用户ID:" + userID + "    执行:" + methodName + "   返回:" + keysJson);
    }
}
