package com.dtask.DTask.userModule.service.impl;

import com.dtask.DTask.userModule.bo.RoleBo;
import com.dtask.DTask.userModule.dao.RoleDao;
import com.dtask.DTask.userModule.service.IRole;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Created by zhong on 2020-4-5.
 */
@Service
public class RoleImpl implements IRole{
    @Autowired
    RoleDao roleDao;

    @Override
    public ResponseData addRole(String roleName) {
        roleDao.addRole(roleName);
        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData editRole(int roleID, String roleName) {
        roleDao.editRole(1,roleName);
        return new ResponseData(1,"编辑成功",null);
    }

    @Override
    public ResponseData getRole() {
        List<RoleBo> roleList = roleDao.getAllRole();
        return new ResponseData(1,"查询成功",roleList);
    }

    @Override
    public ResponseData deleteRole(int[] roleID) {
        try {
            roleDao.deleteRole(roleID);
            return new ResponseData(1,"删除成功",null);
        }catch (Exception e){
            return new ResponseData(2,"删除失败",null);
        }

    }
}
