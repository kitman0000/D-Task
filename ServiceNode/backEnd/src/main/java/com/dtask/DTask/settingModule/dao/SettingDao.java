package com.dtask.DTask.settingModule.dao;

import com.dtask.DTask.settingModule.bo.SettingBo;
import com.dtask.DTask.settingModule.entity.SettingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2020-7-2.
 */
@Mapper
@Repository
public interface SettingDao {
    SettingBo getSettings();

    void updateSettings(@Param("settingEntity") SettingEntity settingEntity);
}
