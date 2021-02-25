package com.dtask.DTask.localTaskModule.controller;

import com.dtask.DTask.localTaskModule.service.ILocalSubTaskChart;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LocalSubTaskChartCl {
    @Autowired
    ILocalSubTaskChart localSubTaskChart;

    @RequestMapping(value = "/api/localTask/localSubTaskChart",method = RequestMethod.GET)
    public ResponseData getLocalSubTaskChart(int day,int taskID){
        return localSubTaskChart.getLocalSubTaskChart(day,taskID);
    }

}
