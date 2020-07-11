package com.dtask.DTask.remoteTaskModule.service.impl;

import com.dtask.DTask.remoteTaskModule.entity.RemoteSubTaskEntity;
import com.dtask.DTask.remoteTaskModule.service.IRemoteSubTask;
import com.dtask.common.ResponseData;
import com.dtask.common.config.RabbitSender;
import com.dtask.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-7-5.
 */
@Service
public class RemoteSubTaskImpl implements IRemoteSubTask {
    @Autowired
    RabbitSender rabbitSender;

    @Override
    public ResponseData addRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity) {
        String res = rabbitSender.send("dtask.remoteSubTask.addRemoteSubTask",
                JsonUtil.objectToJson(remoteSubTaskEntity));

        return handleResponse(res,false);
    }

    @Override
    public ResponseData editRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity) {
        String res = rabbitSender.send("dtask.remoteSubTask.editRemoteSubTask",
                JsonUtil.objectToJson(remoteSubTaskEntity));

        return handleResponse(res,false);
    }

    @Override
    public ResponseData deleteRemoteSubTask(int[] id) {
        String res = rabbitSender.send("dtask.remoteSubTask.deleteRemoteSubTask",
                JsonUtil.objectToJson(id));

        return handleResponse(res,false);
    }

    @Override
    public ResponseData getRemoteSubTaskNumber(RemoteSubTaskEntity remoteSubTaskEntity) {
        String res = rabbitSender.send("dtask.remoteSubTask.getRemoteSubTaskNumber",
                JsonUtil.objectToJson(remoteSubTaskEntity));

        return handleResponse(res,true);
    }

    @Override
    public ResponseData getRemoteSubTaskList(RemoteSubTaskEntity remoteSubTaskEntity) {
        String res = rabbitSender.send("dtask.remoteSubTask.getRemoteSubTaskList",
                JsonUtil.objectToJson(remoteSubTaskEntity));

        return handleResponse(res,true);
    }

    @Override
    public ResponseData editRemoteSubTaskStatus(RemoteSubTaskEntity remoteSubTaskEntity) {
        String res = rabbitSender.send("dtask.remoteSubTask.editRemoteSubTaskStatus",
                JsonUtil.objectToJson(remoteSubTaskEntity));

        return handleResponse(res,false);
    }

    /**
     * 处理从消息队列响应的消息，返回一个ResponseData对象
     * @param res 从消息队列响应的消息
     * @param hasData 返回的信息是否包含数据
     * @return ResponseData对象
     */
    private ResponseData handleResponse(String res,boolean hasData){
        // 如果消息为SYS_FAILED，说明中心调配服务发生错误，返回错误到前端
        if(res.equals("SYS_FAILED")){
            return new ResponseData(2,"系统错误",null);
        }

        if(hasData) {
            // 如果包括数据，把数据封装在ResponseData对象后，返回查询成功
            return new ResponseData(1, "查询成功", res);
        }else {
            // 如果不包括数据，直接返回操作结果
            return new ResponseData(1, res, null);
        }
    }
}