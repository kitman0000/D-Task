package com.dtask.center.bindingModule.controller;

import com.dtask.center.bindingModule.bo.NodeBo;
import com.dtask.center.bindingModule.entity.AskBindingEntity;
import com.dtask.center.bindingModule.entity.HandleBindingEntity;
import com.dtask.center.bindingModule.entity.NodeEntity;
import com.dtask.center.bindingModule.service.IBinding;
import com.dtask.common.config.RabbitSender;
import com.dtask.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhong on 2020-5-13.
 */
@Component
public class BindingCl {
    private Logger logger = LoggerFactory.getLogger(BindingCl.class);

    @Autowired
    IBinding banding;

    @Autowired
    RabbitSender rabbitSender;

    @RabbitListener(queues = "dtask.binding.ask")
    public String askBanding(String msg){
        try {
            AskBindingEntity askBindingEntity = (AskBindingEntity) JsonUtil.jsonToObject(msg, AskBindingEntity.class);
            return banding.askBanding(askBindingEntity);
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.addNode")
    public void addNode(String msg){
        try {
            NodeEntity nodeEntity = (NodeEntity) JsonUtil.jsonToObject(msg, NodeEntity.class);
            banding.addNode(nodeEntity);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @RabbitListener(queues = "dtask.binding.handle")
    @Transactional
    public String handleRequest(String msg){
        try {
            HandleBindingEntity handleBindingEntity = (HandleBindingEntity)JsonUtil.jsonToObject(msg,HandleBindingEntity.class);
            return banding.handleBinding(handleBindingEntity);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "BIND_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.getNodeID")
    public String getNodeID(String msg)
    {
        try {
            NodeEntity nodeEntity = (NodeEntity) JsonUtil.jsonToObject(msg, NodeEntity.class);
            int nodeID = banding.getNodeID(nodeEntity.getNodeName());

            return String.valueOf(nodeID);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.getAllNodes")
    public String getAllNodes()
    {
        try {
            return JsonUtil.objectToJson(banding.getAllNodes());
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.binding.unbind")
    public String unbind(String msg)
    {
        try {
            NodeEntity nodeEntity = (NodeEntity) JsonUtil.jsonToObject(msg, NodeEntity.class);
            return banding.unbind(nodeEntity);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    /**
     * 获取当前节点可处理的请求
     */
    @RabbitListener(queues = "dtask.binding.getBindRequest")
    public String getBindRequest(String msg){
        try {
            NodeEntity nodeEntity = (NodeEntity) JsonUtil.jsonToObject(msg, NodeEntity.class);
            return JsonUtil.objectToJson(banding.getBindRequest(nodeEntity));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }
    @RabbitListener(queues = "dtask.binding.setRoot")
    public String setRoot(String msg){
        try {
            NodeEntity nodeEntity = (NodeEntity) JsonUtil.jsonToObject(msg, NodeEntity.class);
            return JsonUtil.objectToJson(banding.setRoot(nodeEntity));
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.test"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.test"
    ))
    public String test(String msg){
        logger.info("测试" + msg);
        return "ok";
    }



}
