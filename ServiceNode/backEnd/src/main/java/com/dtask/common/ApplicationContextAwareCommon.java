package com.dtask.common;

import com.dtask.DTask.accountModule.service.ITestInterface;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhong on 2021-2-12.
 */
@Component
public class ApplicationContextAwareCommon implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /***
     * 获取一个接口的所有实现
     * @param <T>
     * @return
     */
    public <T> Map<String,T> getImplementsMap(Class c){

        Map<String,Object> interfaceMap = applicationContext.getBeansOfType(c);

        Map<String,T> map = new HashMap<>();

        try {
            interfaceMap.forEach((K,V)->{
                map.put(K,(T)V);
            });
        }catch (Exception ex){
            return null;
        }

        return map;
    }
}
