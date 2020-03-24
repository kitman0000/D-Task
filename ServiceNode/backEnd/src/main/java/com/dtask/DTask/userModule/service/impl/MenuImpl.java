package com.dtask.DTask.userModule.service.impl;

import com.dtask.DTask.accountModule.dao.AccountDao;
import com.dtask.DTask.userModule.bo.ChildMenuBo;
import com.dtask.DTask.userModule.bo.ParentMenuBo;
import com.dtask.DTask.userModule.bo.RoleBo;
import com.dtask.DTask.userModule.dao.MenuDao;
import com.dtask.DTask.userModule.dao.RoleDao;
import com.dtask.DTask.userModule.service.IMenu;
import com.dtask.common.ResponseData;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-3-20.
 */
@Service
public class MenuImpl implements IMenu{
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuDao menuDao;

    Logger logger = LoggerFactory.getLogger(MenuImpl.class);

    @Override
    public ResponseData getUserMenu() {
         logger.info(String.valueOf(SecurityUtils.getSubject().getPrincipal() == null));

        // 获取登录用户名
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        // 获取用户id
        int userID = accountDao.findUserIDByName(username).getId();
        // 获取用户角色
        RoleBo roleBo = roleDao.getUserRole(userID);

        List<ParentMenuBo> parentMenuBoList = menuDao.getRoleMenu(roleBo.getRoleID());
        for(ParentMenuBo parentMenuBo : parentMenuBoList){
            List<ChildMenuBo> childMenuBo = menuDao.getRoleChildMenu(roleBo.getRoleID(),parentMenuBo.getId());
            parentMenuBo.setChildMenuBoList(childMenuBo);
        }

        return new ResponseData(1,"查询成功",parentMenuBoList);

    }

    @Override
    public ResponseData getAllMenu() {
        return null;
    }

    @Override
    public ResponseData addMenu() {
        return null;
    }

    @Override
    public ResponseData editMenu() {
        return null;
    }

    @Override
    public ResponseData deleteMenu() {
        return null;
    }
}
