package com.dtask.DTask.localTaskModule.dao;

import com.dtask.DTask.localTaskModule.bo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LocalSubTaskChartDao {
    List<ChartStatusBo> getChartStatusCount(@Param("day") int day, @Param("taskID") int taskID);

    List<ChartStarBo> getChartStarCount(@Param("day") int day, @Param("taskID") int taskID);

    List<ChartLevelBo> getChartLevelCount(@Param("day") int day, @Param("taskID") int taskID);
}
