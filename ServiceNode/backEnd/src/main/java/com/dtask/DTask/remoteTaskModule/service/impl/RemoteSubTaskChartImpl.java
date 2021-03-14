package com.dtask.DTask.remoteTaskModule.service.impl;


import com.MQClouder.EncryptRabbitSender;
import com.dtask.DTask.remoteTaskModule.entity.RemoteChartEntity;
import com.dtask.DTask.remoteTaskModule.service.IRemoteSubTaskChart;
import com.dtask.common.ResponseData;
import com.dtask.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoteSubTaskChartImpl implements IRemoteSubTaskChart {

    @Autowired
    private EncryptRabbitSender rabbitSender;

    public ResponseData getRemoteSubTaskChart(RemoteChartEntity chartEntity) {
        String result = rabbitSender.encryptSend("dtask.remoteSubTask.getRemoteSubTaskChart", JsonUtil.objectToJson(chartEntity));
        return new ResponseData(1, "2", result);
    }
}
