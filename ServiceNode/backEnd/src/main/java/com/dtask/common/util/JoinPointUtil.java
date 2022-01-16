package com.dtask.common.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Author: Kitman0000
 * Date:   2021-10-10
 * Intro:
 **/
public class JoinPointUtil {
    public static <T> T getParameterValue(JoinPoint joinPoint, String param){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            if(parameterNames[i].equals(param)){
                return (T)joinPoint.getArgs()[i];
            }
        }

        return null;
    }
}
