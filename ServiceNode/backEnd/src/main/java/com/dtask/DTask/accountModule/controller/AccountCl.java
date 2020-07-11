package com.dtask.DTask.accountModule.controller;

import com.dtask.DTask.accountModule.entity.RemoteLoginEntity;
import com.dtask.DTask.accountModule.service.IAccount;
import com.dtask.common.ResponseData;
import com.dtask.common.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-3-12.
 */
@RestController
@PropertySource(value="classpath:application.properties",encoding = "utf-8")
public class AccountCl {

    @Autowired
    IAccount account;

    @RequestMapping(value = "/api/account/localLogin",method = RequestMethod.POST)
    public ResponseData localLogin(String username,String pwd)
    {
        return account.login(username,pwd);
    }

    @RequestMapping(value = "/api/account/localLogout",method = RequestMethod.POST)
    public ResponseData localLogout()
    {
        return account.logout();
    }

    @RequestMapping(value = "/api/account/remoteLogin",method = RequestMethod.POST)
    public ResponseData remoteLogin(RemoteLoginEntity remoteLoginEntity){
        return account.remoteLogin(remoteLoginEntity);
    }

    @RequestMapping(value = "/api/account/parentNodes",method = RequestMethod.GET)
    public ResponseData getParentNodes(){
        return account.getParentNodes();
    }

    /**
     * 处理消息队列发送至本节点的登录请求，即本节点用户申请登录至其他节点
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.account.login." + "#{${nodeName}}"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.account.login." + "#{${nodeName}}"
    ))
    public String mqRemoteLogin(String msg){
        try {
            RemoteLoginEntity remoteLoginEntity = (RemoteLoginEntity) JsonUtil.jsonToObject(msg,RemoteLoginEntity.class);
            return account.mqRemoteLogin(remoteLoginEntity.getUsername(),remoteLoginEntity.getPwd());
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }

    }

}
