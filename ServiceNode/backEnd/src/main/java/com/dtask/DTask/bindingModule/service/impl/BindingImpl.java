package com.dtask.DTask.bindingModule.service.impl;

import com.dtask.DTask.bindingModule.service.IBinding;
import com.dtask.common.NodeCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.config.RabbitSender;
import com.dtask.common.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-6-14.
 */
@Service
public class BindingImpl implements IBinding{
    @Autowired
    RabbitSender rabbitSender;

    @Autowired
    CacheUtil cacheUtil;

    @Value("${nodeName}")
    private String nodeName;

    @Override
    public ResponseData getAllNodes() {
        String res = rabbitSender.send("dtask.getAllNodes","");
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData askBinding(int requestBindID) {
        int nodeID = NodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
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
        int nodeID = NodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        String res = rabbitSender.send("dtask.binding.getBindRequest","{\"nodeID\":"+nodeID+"}");
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData handleBindingRequest(int request, boolean accept) {
        int nodeID = NodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }
        String res = rabbitSender.send("dtask.binding.handle","{\"requestID\":"+request+",\"accept\":"+accept+",\"nodeID\":" + nodeID + "}");
        return new ResponseData(1,res,null);
    }

    @Override
    public ResponseData setRoot() {
        int nodeID = NodeCommon.getNodeID();
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
            return new ResponseData(2,res,null);
        }
    }

    @Override
    public ResponseData unbind() {
        int nodeID = NodeCommon.getNodeID();
        if(nodeID == -1){
            return new ResponseData(2,"SYS_FAILED",null);
        }

        String res = rabbitSender.send("dtask.binding.unbind","{\"nodeID\":\""+nodeID+"\"}");

        if(res.equals("UNBIND_SUCCESS")){
            return new ResponseData(1,"解绑成功",null);
        }else if(res.equals("UNBIND_FAILED")){
            return new ResponseData(2,"解绑失败",null);
        }else {
            // 未知错误
            return new ResponseData(2,res,null);
        }
    }

    /** 以下为自动操作 **/

    @Override
    public void addNode() {
        String sendJsonMsg = "{\"nodeName\":\""+nodeName+"\"}";
        rabbitSender.sendWithoutResponse("dtask.addNode",sendJsonMsg);
    }

    @Override
    public void getNodeID() {
        String nodeID = rabbitSender.send("dtask.getNodeID","{\"nodeName\":\"" + nodeName + "\"}");
        cacheUtil.write("nodeID",nodeID);
    }
}