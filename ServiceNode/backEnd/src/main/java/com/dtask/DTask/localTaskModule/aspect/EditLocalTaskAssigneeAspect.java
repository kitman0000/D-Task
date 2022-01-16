package com.dtask.DTask.localTaskModule.aspect;

import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.common.UserCommon;
import com.dtask.common.util.IntegrationUtil;
import com.dtask.common.util.JoinPointUtil;
import com.dtask.common.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhong on 2022-1-13.
 */
@Component
@Aspect
public class EditLocalTaskAssigneeAspect {
    @Autowired
    private IntegrationUtil integrationUtil;

    @Value("${arcAddress}")
    private String arcAddress;   

    Logger logger = LoggerFactory.getLogger(LocalTaskAspect.class);

    @Pointcut("execution(public * com.dtask.DTask.localTaskModule.service.impl.LocalSubTaskImpl.editLocalSubTaskAssigneeByUser(..))")
    public void localTaskPointCut() {}

    @Before("localTaskPointCut()")
    public void doBeforePointCut(JoinPoint joinPoint) {
        int userID = UserCommon.getUserBo().getUserID();
        String methodName = joinPoint.getSignature().getName();
        String args = JsonUtil.objectToJson(joinPoint.getArgs());

        logger.info("用户ID:" + userID + "    执行:" + methodName + "   参数:" + args);
    }

    @AfterReturning(value = "localTaskPointCut()",returning = "keys")
    public void doAfterPointCut(JoinPoint joinPoint, Object keys){
        boolean returning = (boolean)keys;
        LocalSubTaskEntity localSubTaskEntity = JoinPointUtil.getParameterValue(joinPoint,"localSubTaskEntity");

        // 审核Task
        if (localSubTaskEntity.getTaskID() != 1 || !returning){
            return;
        }

        Map<String,String> paramsMap = new HashMap<String,String>(){
            {
                put("id",String.valueOf(localSubTaskEntity.getId()));
                put("assignee",String.valueOf(localSubTaskEntity.getAssignee()));
            }
        };

        integrationUtil.post(arcAddress + "/DTask/assignRankApply",paramsMap,false);

    }
}
