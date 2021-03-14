package com.dtask.center.remoteTaskModule.controller;

import com.MQClouder.MessageReceiver;
import com.dtask.center.remoteTaskModule.entity.RemoteChartEntity;
import com.dtask.center.remoteTaskModule.service.IRemoteSubTaskChart;
import com.dtask.common.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2021-3-7.
 */
@Component
public class RemoteSubTaskChartCl {

    @Autowired
    private IRemoteSubTaskChart remoteSubTaskChart;

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteSubTask.getRemoteSubTaskChart"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteSubTask.getRemoteSubTaskChart"
    ))
    public String getRemoteSubTaskChart(String msg){
        RemoteChartEntity remoteChartEntity = (RemoteChartEntity) JsonUtil.jsonToObject(msg,RemoteChartEntity.class);
        Object var = remoteSubTaskChart.getRemoteSubTaskChart(remoteChartEntity);
        return JsonUtil.objectToJson(var);
    }
}
