package com.dtask.DTask.remoteTaskModule.bo;

import com.dtask.DTask.remoteTaskModule.bo.ChartLevelBo;
import com.dtask.DTask.remoteTaskModule.bo.ChartStarBo;
import com.dtask.DTask.remoteTaskModule.bo.ChartStatusBo;
import lombok.Data;

import java.util.List;

@Data
public class RemoteSubTaskChartBo {
    List<ChartLevelBo> chartLevelBo;

    List<ChartStarBo> chartStarBo;

    List<ChartStatusBo> chartStatusBo;
}
