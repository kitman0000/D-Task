package com.dtask.DTask;

import com.dtask.DTask.localTaskModule.entity.LocalTaskSearchEntity;
import com.dtask.DTask.localTaskModule.service.impl.LocalTaskImpl;
import com.dtask.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhong on 2020-7-6.
 */

@RunWith(SpringRunner.class)
@Component
@SpringBootTest
public class LocalTaskTest {

    @Autowired
    private LocalTaskImpl localTask;

    Logger logger = LoggerFactory.getLogger(LocalTaskTest.class);

    @Test
    public void addTask() {
        for(int i = 0;i<100;i++) {
            localTask.addLocalTask("测试任务" + i);
        }
    }

    @Test
    public void editTask(){
        localTask.editLocalTask(1,"调试任务1", 1001,false);
    }

    @Test
    public void deleteTask(){
        localTask.deleteLocalTask(1);
    }

    @Test
    public void getLocalTaskNumber(){
        LocalTaskSearchEntity searchEntity = new LocalTaskSearchEntity();
        searchEntity.setTaskCreatorID(-1);
        searchEntity.setTaskName("测试任务");
        Object o = localTask.getLocalTaskNumber(searchEntity);
        logger.info(JsonUtil.objectToJson(o));
    }

    @Test
    public void getLocalTaskPage(){
        LocalTaskSearchEntity searchEntity = new LocalTaskSearchEntity();
        searchEntity.setTaskCreatorID(-1);
        searchEntity.setTaskName("测试任务");
        Object o = localTask.getLocalTaskList(searchEntity,1);
        logger.info(JsonUtil.objectToJson(o));
    }

    @Test
    public void addLocalTaskMember(){
        for(int i = 1000;i <= 1017;i++){
            Object o1 = localTask.addLocalTaskMember(1,i);
            Object o2 = localTask.addLocalTaskMember(2,i);
            Object o3 = localTask.addLocalTaskMember(3,i);

            logger.info(JsonUtil.objectToJson(o1));
            logger.info(JsonUtil.objectToJson(o2));
            logger.info(JsonUtil.objectToJson(o3));
        }
    }

    @Test
    public void getLocalTaskMember(){
        Object o = localTask.getLocalTaskMember(1);
        logger.info(JsonUtil.objectToJson(o));
    }

    @Test
    public void deleteLocalTaskMember(){
        localTask.removeLocalTaskMember(1,1002);
    }

    @Test
    public void toggleLocalTaskAdmin(){
        localTask.toggleTaskAdmin(1,1003,true);
    }
}
