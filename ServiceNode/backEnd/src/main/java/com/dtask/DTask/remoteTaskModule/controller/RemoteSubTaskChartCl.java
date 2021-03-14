package com.dtask.DTask.remoteTaskModule.controller;


import com.dtask.DTask.remoteTaskModule.entity.RemoteChartEntity;
import com.dtask.DTask.remoteTaskModule.service.IRemoteSubTaskChart;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteSubTaskChartCl {
    @Autowired
    IRemoteSubTaskChart RemoteSubTaskChart;

    @RequestMapping(value = "/api/remoteTask/remoteSubTaskChart",method = RequestMethod.GET)
    public ResponseData getRemoteSubTaskChart(int day, int taskID){

        RemoteChartEntity remoteChartEntity = new RemoteChartEntity();
        remoteChartEntity.setDay(day).setTaskID(taskID);

        return RemoteSubTaskChart.getRemoteSubTaskChart(remoteChartEntity);
    }
}
