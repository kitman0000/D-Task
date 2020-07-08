package com.dtask.DTask.bindingModule.service.impl;

import com.dtask.DTask.bindingModule.service.IBinding;
import com.dtask.common.NodeCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.config.RabbitSender;
import com.dtask.common.util.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-6-14.
 */
@Service
@PropertySource(value="classpath:application.properties",encoding = "utf-8")
public class BindingImpl implements IBinding{
    @Autowired
    RabbitSender rabbitSender;

    @Autowired
    CacheUtil cacheUtil;

    @Value("${nodeName}")
    private String nodeName;

    @Autowired
    NodeCommon nodeCommon;

    @Override
    public ResponseData getAllNodes() {
        String res = rabbitSender.send("dtask.getAllNodes","");
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData askBinding(int requestBindID) {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        if(requestBindID == nodeID){
            // 当前端试图申请绑定的节点等于本节点时。返回错误
            return new ResponseData(3,"BINDING_YOURSELF",null);
        }

        String res = rabbitSender.send("dtask.binding.ask","{\"nodeID\":"+nodeID+",\"requestBindID\":"+requestBindID+"}");
        if(res.equals("BIND_SUCCESS")){
            return new ResponseData(1,"申请成功",null);
        }else {
            return new ResponseData(2,res,null);
        }
    }

    @Override
    public ResponseData getBindRequest() {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        String res = rabbitSender.send("dtask.binding.getBindRequest","{\"nodeID\":"+nodeID+"}");
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData handleBindingRequest(int request, boolean accept) {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        String res = rabbitSender.send("dtask.binding.handle","{\"requestID\":"+request+",\"accept\":"+accept+",\"nodeID\":" + nodeID + "}");
        return new ResponseData(1,res,null);
    }

    @Override
    public ResponseData setRoot() {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        String res = rabbitSender.send("dtask.binding.setRoot","{\"nodeID\":"+nodeID+"}");
        if(res.equals("SET_SUCCESS")){
            return new ResponseData(1,"设置成功",null);
        }else if(res.equals("ROOT_EXIST")){
            return new ResponseData(2,"根节点已存在",null);
        }else {
            // 未知错误
            return new ResponseData(3,res,null);
        }
    }

    @Override
    public ResponseData unbind() {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }

        String res = rabbitSender.send("dtask.binding.unbind","{\"nodeID\":\""+nodeID+"\"}");

        if(res.equals("UNBIND_SUCCESS")){
            return new ResponseData(1,"解绑成功",null);
        }else if(res.equals("UNBIND_FAILED")){
            // 当自身没有绑定时，返回解绑失败
            return new ResponseData(2,"解绑失败",null);
        }else {
            // 未知错误
            return new ResponseData(3,res,null);
        }
    }

    /** 以下为自动操作 **/

    @Override
    public void addNode() {
        String sendJsonMsg = "{\"nodeName\":"+nodeName+"}";
        rabbitSender.sendWithoutResponse("dtask.addNode",sendJsonMsg);
    }

    @Override
    public void getNodeID() {
        String nodeID = rabbitSender.send("dtask.getNodeID","{\"nodeName\":" + nodeName + "}");
        cacheUtil.write("nodeID",nodeID);
    }
}
