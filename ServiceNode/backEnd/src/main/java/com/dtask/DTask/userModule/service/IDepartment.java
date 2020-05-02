package com.dtask.DTask.userModule.service;

import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-5-2.
 */
public interface IDepartment {
    ResponseData addDepartment(String name);

    ResponseData editDepartment(int id,String name);

    ResponseData deleteDepartment(int id);

    ResponseData getDepartment();
}
