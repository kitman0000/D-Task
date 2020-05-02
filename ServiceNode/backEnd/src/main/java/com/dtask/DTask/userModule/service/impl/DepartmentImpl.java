package com.dtask.DTask.userModule.service.impl;

import com.dtask.DTask.userModule.bo.DepartmentBo;
import com.dtask.DTask.userModule.dao.DepartmentDao;
import com.dtask.DTask.userModule.service.IDepartment;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-5-2.
 */

@Service
public class DepartmentImpl implements IDepartment {
    @Autowired
    DepartmentDao departmentDao;

    @Override
    public ResponseData addDepartment(String name) {
        departmentDao.addDepartment(name);
        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData editDepartment(int id, String name) {
        departmentDao.updateDepartment(id,name);
        return new ResponseData(1,"修改成功",null);
    }

    @Override
    public ResponseData deleteDepartment(int id) {
        departmentDao.deleteDepartment(id);
        return new ResponseData(1,"删除成功",null);
    }

    @Override
    public ResponseData getDepartment() {
        List<DepartmentBo> departmentBoList = departmentDao.getDepartment();
        return new ResponseData(1,"查询成功",departmentBoList);
    }
}
