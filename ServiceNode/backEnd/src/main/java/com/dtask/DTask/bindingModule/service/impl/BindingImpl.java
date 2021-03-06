package com.dtask.DTask.bindingModule.service.impl;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.DTask.bindingModule.service.IBinding;
import com.dtask.common.NodeCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-6-14.
 */
@Service
@PropertySource(value="classpath:application.properties",encoding = "utf-8")
public class BindingImpl implements IBinding{
    @Autowired
    EncryptRabbitSender rabbitSender;

    @Autowired
    CacheUtil cacheUtil;

    @Value("${nodeName}")
    private String nodeName;

    @Autowired
    NodeCommon nodeCommon;

    @Override
    public ResponseData getAllNodes() {
        String res = rabbitSender.encryptSend("dtask.getAllNodes","");
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

        String res = rabbitSender.encryptSend("dtask.binding.ask","{\"nodeID\":"+nodeID+",\"requestBindID\":"+requestBindID+"}");
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
        String res = rabbitSender.encryptSend("dtask.binding.getBindRequest","{\"nodeID\":"+nodeID+"}");
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData handleBindingRequest(int request, boolean accept) {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        String res = rabbitSender.encryptSend("dtask.binding.handle","{\"requestID\":"+request+",\"accept\":"+accept+",\"nodeID\":" + nodeID + "}");
        return new ResponseData(1,res,null);
    }

    @Override
    public ResponseData setRoot() {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        String res = rabbitSender.encryptSend("dtask.binding.setRoot","{\"nodeID\":"+nodeID+"}");
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

        String res = rabbitSender.encryptSend("dtask.binding.unbind","{\"nodeID\":\""+nodeID+"\"}");

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

    // 通过api调用此方法，获取本地缓存的NodeID
    @Override
    public ResponseData apiGetNodeID(){
        int nodeID = nodeCommon.getNodeID();
        return new ResponseData(1,"查询成功",nodeID);
    }

    /** 以下为自动操作 **/

    @Override
    public void addNode() {
        String sendJsonMsg = "{\"nodeName\":"+nodeName+"}";
        rabbitSender.encryptSendWithoutResponse("dtask.addNode",sendJsonMsg);
    }

    @Override
    public void getNodeID() {
        String nodeID = rabbitSender.encryptSend("dtask.getNodeID","{\"nodeName\":" + nodeName + "}");
        cacheUtil.write("nodeID",nodeID);
    }

    @Override
    public void sendKeepAlive() {
        Object nodeID = String.valueOf(cacheUtil.read("nodeID"));

        if (nodeID == null){
            // 尚未获取到nodeID，也可能是没有连接到MQ，不继续发送心跳包
            return;
        }

        rabbitSender.encryptSendWithoutResponse("dtask.keepAlive",String.valueOf(nodeID));
    }
}
