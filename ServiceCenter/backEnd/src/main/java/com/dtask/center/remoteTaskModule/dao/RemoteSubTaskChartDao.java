package com.dtask.center.remoteTaskModule.dao;

import com.dtask.center.remoteTaskModule.bo.ChartLevelBo;
import com.dtask.center.remoteTaskModule.bo.ChartStarBo;
import com.dtask.center.remoteTaskModule.bo.ChartStatusBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2021-3-8.
 */
@Mapper
@Repository
public interface RemoteSubTaskChartDao {
    List<ChartStatusBo> getChartStatusCount(@Param("day") int day, @Param("taskID") int taskID);

    List<ChartStarBo> getChartStarCount(@Param("day") int day, @Param("taskID") int taskID);

    List<ChartLevelBo> getChartLevelCount(@Param("day") int day, @Param("taskID") int taskID);
}
