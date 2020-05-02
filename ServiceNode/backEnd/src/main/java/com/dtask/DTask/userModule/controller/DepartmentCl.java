package com.dtask.DTask.userModule.controller;

import com.dtask.DTask.userModule.service.IDepartment;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-5-2.
 */
@RestController
public class DepartmentCl {
    @Autowired
    IDepartment department;

    @RequestMapping(value = "/api/department/department",method = RequestMethod.POST)
    ResponseData addDepartment(String name){
        return department.addDepartment(name);
    }

    @RequestMapping(value = "/api/department/department",method = RequestMethod.PUT)
    ResponseData editDepartment(int id,String name){
        return department.editDepartment(id,name);
    }

    @RequestMapping(value = "/api/department/department",method = RequestMethod.DELETE)
    ResponseData deleteDepartment(int id){
         return department.deleteDepartment(id);
    }

    @RequestMapping(value = "/api/department/department",method = RequestMethod.GET)
    ResponseData getDepartment(){
        return department.getDepartment();
    }
}
