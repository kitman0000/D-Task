package com.dtask.DTask.settingModule.service.impl;

import com.dtask.DTask.bindingModule.controller.BindingCl;
import com.dtask.DTask.bindingModule.service.impl.BindingImpl;
import com.dtask.DTask.settingModule.dao.SettingDao;
import com.dtask.DTask.settingModule.entity.SettingEntity;
import com.dtask.DTask.settingModule.service.ISetting;
import com.dtask.common.ResponseData;
import com.dtask.common.config.WebsiteConfig;
import com.dtask.common.util.CacheUtil;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-7-2.
 */
@Service
public class SettingImpl implements ISetting ,ApplicationRunner{
    @Autowired
    private SettingDao settingDao;

    @Autowired
    private BindingImpl binding;

    @Autowired
    private CacheUtil cacheUtil;

    Logger logger = LoggerFactory.getLogger(SettingImpl.class);

    @Override
    public ResponseData getSetting() {
        Object settings = settingDao.getSettings();
        return new ResponseData(1,"查询成功",settings);
    }

    /**
     *  更新网站设置
     *  缓存使用方式需要重启网站才能生效，其他可以立即生效
     */
    @Override
    public ResponseData updateSetting(SettingEntity settingEntity) {
        // 设置网站名称
        WebsiteConfig.setWebsiteName(settingEntity.getWebsiteName());
        // 设置网站最多登录失败次数
        WebsiteConfig.setMaxLoginTime(settingEntity.getMaxLogin());
        // 设置是否开启任务日志
        WebsiteConfig.setTaskLog(settingEntity.isTaskLog());
        // 设置是否开启用户操作日志
        WebsiteConfig.setUserLog(settingEntity.isUserLog());

        // 更新数据库
        settingDao.updateSettings(settingEntity);

        return new ResponseData(1,"设置成功",null);
    }

    /**
     * 关闭系统后台
     * 关闭系统后只能通过登录服务器重新启动
     */
    @Override
    public ResponseData shutDown() {
        System.exit(0);
        return null;
    }

    /**
     *  由于无法使用@Order修改类加载顺序，在这里调用所有初始化操作
     *  包括缓存设置，节点操作
     */
    @Order(value = 1)
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        // 缓存设置
        int cacheMode = settingDao.getSettings().getCache();
        cacheUtil.setCacheMode(cacheMode);

        // 获取节点ID
        binding.addNode();
        Thread.sleep(3000);
        binding.getNodeID();
    }


}
