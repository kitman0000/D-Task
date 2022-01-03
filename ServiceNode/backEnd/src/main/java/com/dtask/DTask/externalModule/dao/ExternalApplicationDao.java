package com.dtask.DTask.externalModule.dao;

import com.dtask.DTask.externalModule.bo.ApplicationBo;
import com.dtask.DTask.externalModule.bo.ExternalUrlBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2022-1-1.
 */
@Mapper
@Repository
public interface ExternalApplicationDao {
    ApplicationBo getAvailableApplication(@Param("name") String name, @Param("password") String password);

    List<ExternalUrlBo> getApplicationUrl(int applicationID);
}
