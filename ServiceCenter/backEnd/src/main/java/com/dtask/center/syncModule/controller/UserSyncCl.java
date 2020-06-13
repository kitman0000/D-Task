package com.dtask.center.syncModule.controller;

import com.dtask.center.syncModule.entity.SyncUserInfoEntity;
import com.dtask.center.syncModule.entity.UserListEntity;
import com.dtask.center.syncModule.service.ISyncUserInfo;
import com.dtask.common.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2020-6-11.
 */
@Component
public class UserSyncCl {

    @Autowired
    ISyncUserInfo syncUserInfo;

    /**
     * 同步子节点的用户数据
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.syncUserInfo"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.syncUserInfo"
    ))
    public String syncUserInfo(String msg){

        try {
            SyncUserInfoEntity syncUserInfoEntity = (SyncUserInfoEntity) JsonUtil.jsonToObject(msg, SyncUserInfoEntity.class);
            return syncUserInfo.syncUserInfo(syncUserInfoEntity);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }
}
