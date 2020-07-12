package com.dtask.center.accountModule.controller;

import com.dtask.center.accountModule.service.IAccount;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-7-10.
 */
@Component
public class AccountCl {
    @Autowired
    IAccount account;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.account.remoteLogin"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.account.remoteLogin"
    ))
    public String remoteLogin(String msg){
        try {
            return account.remoteLogin(msg);
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    /**
     * 获取节点的父节点
     * @param msg 节点ID
     * @return 父节点
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.account.getParentNodes"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.account.getParentNodes"
    ))
    public String getParentNodes(String msg){
        try {
            return account.getParentNodes(Integer.valueOf(msg));
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }
}
