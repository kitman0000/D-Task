package com.dtask.DTask.localTaskModule.service.impl;

import com.dtask.DTask.localTaskModule.bo.ChartLevelBo;
import com.dtask.DTask.localTaskModule.bo.ChartStarBo;
import com.dtask.DTask.localTaskModule.bo.ChartStatusBo;
import com.dtask.DTask.localTaskModule.bo.LocalSubTaskChartBo;
import com.dtask.DTask.localTaskModule.dao.LocalSubTaskChartDao;
import com.dtask.DTask.localTaskModule.service.ILocalSubTaskChart;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalSubTaskChartImpl implements ILocalSubTaskChart {
    @Autowired
    private LocalSubTaskChartDao localSubTaskChartDao;

    public ResponseData getLocalSubTaskChart(int day,int taskID){
        List<ChartLevelBo> levelCountList=localSubTaskChartDao.getChartLevelCount(day,taskID);
        List<ChartStarBo> starCountList=localSubTaskChartDao.getChartStarCount(day,taskID);
        List<ChartStatusBo> statusCountList=localSubTaskChartDao.getChartStatusCount(day,taskID);
        LocalSubTaskChartBo localSubTaskChartBo = new LocalSubTaskChartBo();
        localSubTaskChartBo.setChartLevelBo(levelCountList)
                .setChartStarBo(starCountList)
                .setChartStatusBo(statusCountList);

        return new ResponseData(1,"2",localSubTaskChartBo);
    }

}
