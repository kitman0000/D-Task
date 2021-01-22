package com.dtask.DTask.remoteTaskModule.service.impl;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.DTask.remoteTaskModule.entity.RemoteTaskSearchEntity;
import com.dtask.DTask.remoteTaskModule.service.IRemoteTask;
import com.dtask.common.NodeCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-7-2.
 */
@Service
public class RemoteTaskImpl implements IRemoteTask{
    @Autowired
    EncryptRabbitSender rabbitSender;

    @Autowired
    NodeCommon nodeCommon;

    @Override
    public ResponseData addRemoteTask(String name) {
        int userID = UserCommon.getUserBo().getUserID();
        int nodeID = nodeCommon.getNodeID();

        String res = rabbitSender.encryptSend("dtask.remoteTask.addRemoteTask",
                "{\"name\":\""+name+"\",\"userID\":"+userID+",\"nodeID\":"+nodeID+"}");

        return new ResponseData(1,res,null); // 成功res = ADD_SUCCESS
    }

    @Override
    public ResponseData editRemoteTask(int id, String name, int creator, boolean allowedMemberChangeStatus, int creatorNodeID) {
        int userID = UserCommon.getUserBo().getUserID();

        String res = rabbitSender.encryptSend("dtask.remoteTask.editRemoteTask",
                "{\"id\":"+id+",\"name\":\""+name+"\",\"creator\":"+creator+
                        ",\"allowedMemberChangeStatus\":"+allowedMemberChangeStatus+",\"userID\":"+userID+",\"creatorNode\":"+creatorNodeID+"}");

        return new ResponseData(1,res,null); // 成功 res = EDIT_SUCCESS
    }

    @Override
    public ResponseData getTaskDetail(int taskID) {
        String res = rabbitSender.encryptSend("dtask.remoteTask.getTaskDetail",String.valueOf(taskID));
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData deleteRemoteTask(int id) {
        String res = rabbitSender.encryptSend("dtask.remoteTask.deleteRemoteTask","{\"id\":"+id+"}");

        return new ResponseData(1,res,null); // 成功 res = DELETE_SUCCESS
    }

    @Override
    public ResponseData getRemoteTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        String res = rabbitSender.encryptSend("dtask.remoteTask.getRemoteTaskNumber",
                "{\"taskName\":\""+remoteTaskSearchEntity.getTaskName()+"\"}");

        return new ResponseData(1,"查询成功",res); // 返回页数
    }

    @Override
    public ResponseData getRemoteTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity) {

        String res = rabbitSender.encryptSend("dtask.remoteTask.getRemoteTaskList",
               JsonUtil.objectToJson(remoteTaskSearchEntity));

        return new ResponseData(1,"查询成功",res); // 返回任务列表
    }

    @Override
    public ResponseData addRemoteTaskMember(int taskID, int newUserID, int newUserNodeID) {
        int nodeID = nodeCommon.getNodeID();
        int userID = UserCommon.getUserBo().getUserID();

        String res = rabbitSender.encryptSend("dtask.remoteTask.addRemoteTaskMember",
                "{\"taskID\":"+taskID+",\"nodeID\":"+nodeID+",\"userID\":"+userID
                        +",\"newUserID\":"+newUserID+",\"newUserNodeID\":"+newUserNodeID+"}");

        if(res.equals("ADD_SUCCESS")){
            return new ResponseData(1,res,null);
        }
        return new ResponseData(2,res,null); // 逻辑或权限错误，返回失败
    }

    @Override
    public ResponseData removeRemoteTaskMember(int taskID, int removeUserID, int removeUserNodeID) {
        int nodeID = nodeCommon.getNodeID();
        int userID = UserCommon.getUserBo().getUserID();

        String res = rabbitSender.encryptSend("dtask.remoteTask.removeRemoteTaskMember",
                "{\"taskID\":"+taskID+",\"userID\":"+userID+",\"nodeID\":"+nodeID
                        +",\"removeUserID\":"+removeUserID+",\"removeUserNodeID\":"+removeUserNodeID+"}");
        if(res.equals("DELETE_SUCCESS")){
            return new ResponseData(1,res,null);
        }

        return new ResponseData(2,res,null); // 逻辑或权限错误，返回失败
    }

    @Override
    public ResponseData getRemoteTaskMember(int taskID) {
            String res = rabbitSender.encryptSend("dtask.remoteTask.getRemoteMember",
                "{\"taskID\":"+taskID+"}");
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData toggleTaskAdmin(int taskID, int toggleUserID, int toggleUserNodeID, boolean isAdmin) {
        int nodeID = nodeCommon.getNodeID();
        int userID = UserCommon.getUserBo().getUserID();

        String res = rabbitSender.encryptSend("dtask.remoteTask.toggleTaskAdmin",
                "{\"taskID\":"+taskID+",\"userID\":"+userID+",\"nodeID\":"+nodeID
                        +",\"isAdmin\":"+isAdmin+",\"toggleUserID\":"+toggleUserID+",\"toggleUserNodeID\":"+ toggleUserNodeID +"}");

        if(res.equals("EDIT_SUCCESS")){
            return new ResponseData(1,res,null);
        }
        return new ResponseData(2,res,null); // 逻辑或权限错误，返回失败

    }

    @Override
    public ResponseData getUserTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int nodeID = nodeCommon.getNodeID();

        String res = rabbitSender.encryptSend("dtask.remoteTask.getUserTaskNumber",
                "{\"taskName\":\""+remoteTaskSearchEntity.getTaskName()+"\",\"userID\":"+userID+",\"nodeID\":"+nodeID+"}");

        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData getUserTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        int nodeID = nodeCommon.getNodeID();

        remoteTaskSearchEntity.setUserID(userID);
        remoteTaskSearchEntity.setNodeID(nodeID);

        String res = rabbitSender.encryptSend("dtask.remoteTask.getUserTaskList",
                JsonUtil.objectToJson(remoteTaskSearchEntity));

        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData getTaskUserRole(int taskID) {
        int userID = UserCommon.getUserBo().getUserID();
        int nodeID = nodeCommon.getNodeID();
        String res = rabbitSender.encryptSend("dtask.remoteTask.getTaskUserRole","{\"userID\":"+userID+",\"nodeID\":"+nodeID+",\"taskID\":"+taskID+"}");
        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData getAllowUserChangeStatus(int taskID) {
        String res = rabbitSender.encryptSend("dtask.remoteTask.getAllowUserChangeStatus",String.valueOf(taskID));
        return new ResponseData(1,"查询成功",res);
    }
}
