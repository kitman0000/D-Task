package com.dtask.DTask;

import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.DTask.localTaskModule.service.ILocalSubTask;
import com.dtask.DTask.localTaskModule.service.impl.LocalTaskImpl;
import com.dtask.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by zhong on 2020-7-6.
 */
@RunWith(SpringRunner.class)
@Component
@SpringBootTest
public class LocalSubTaskTest {

    @Autowired
    private ILocalSubTask localTask;

    Logger logger = LoggerFactory.getLogger(LocalTaskTest.class);

    @Test
    public void addLocalSubTask(){
        for(int i = 2;i < 30;i++) {
//            LocalSubTaskEntity localSubTaskEntity = new LocalSubTaskEntity();
//            localSubTaskEntity.setTaskID(1);
//            localSubTaskEntity.setContent("测试任务内容" + i);
//            localSubTaskEntity.setDeadline(new Date());
//            localSubTaskEntity.setLevel(1);
//            localSubTaskEntity.setStar(2);
//            localSubTaskEntity.setStartTime(new Date());
//            localSubTaskEntity.setName("测试子任务" + i);
//            localSubTaskEntity.setTag("{aaa,vvv,ccc," + i + "}");
//            Object o = localTask.addLocalSubTask(localSubTaskEntity);
//            logger.info(JsonUtil.objectToJson(o));
        }
    }

    @Test
    public void editLocalSubTask(){
//        LocalSubTaskEntity localSubTaskEntity = new LocalSubTaskEntity();
//        localSubTaskEntity.setId(1);
//        localSubTaskEntity.setTaskID(1);
//        localSubTaskEntity.setContent("测试任务内容2");
//        localSubTaskEntity.setDeadline(new Date());
//        localSubTaskEntity.setLevel(1);
//        localSubTaskEntity.setStar(2);
//        localSubTaskEntity.setStartTime(new Date());
//        localSubTaskEntity.setName("测试子任务2");
//        localSubTaskEntity.setTag("{aaa,vvv,ccc,bb}");
//
//        Object o = localTask.editLocalSubTask(localSubTaskEntity);
//        logger.info(JsonUtil.objectToJson(o));
    }

    @Test
    public void getLocalSubTaskNumber(){
        LocalSubTaskEntity localSubTaskEntity = new LocalSubTaskEntity();
        localSubTaskEntity.setTaskID(1);

        Object o = localTask.getLocalSubTaskNumber(localSubTaskEntity);
        logger.info(JsonUtil.objectToJson(o));
    }

    @Test
    public void getLocalSubTask(){
        LocalSubTaskEntity localSubTaskEntity = new LocalSubTaskEntity();
        localSubTaskEntity.setTaskID(1);

        Object o = localTask.getLocalSubTaskList(localSubTaskEntity,1);
        logger.info(JsonUtil.objectToJson(o));
    }

    @Test
    public void editStats(){
        LocalSubTaskEntity localSubTaskEntity = new LocalSubTaskEntity();
        localSubTaskEntity.setTaskID(1);
        localSubTaskEntity.setId(1);
        localSubTaskEntity.setStatus(3);

        Object o = localTask.editLocalSubTaskStatus(localSubTaskEntity);
        logger.info(JsonUtil.objectToJson(o));
    }
}
