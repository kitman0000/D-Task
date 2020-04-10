package com.dtask.DTask.userModule.controller;

import com.dtask.DTask.userModule.service.IRole;
import com.dtask.common.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-4-5.
 */

@RestController
public class RoleCl {

    @Autowired
    IRole role;

    @RequiresPermissions("user:role:add")
    @RequestMapping(value = "/api/role/role",method = RequestMethod.POST)
    public ResponseData addRole(String roleName){
        return role.addRole(roleName);
    }

    @RequiresPermissions("user:role:edit")
    @RequestMapping(value = "/api/role/role",method = RequestMethod.PUT)
    public ResponseData editRole(int roleID,String roleName){
        return role.editRole(roleID,roleName);
    }

    @RequestMapping(value = "/api/role/role",method = RequestMethod.GET)
    public ResponseData getRole(){
        return role.getRole();
    }

    @RequiresPermissions("user:role:delete")
    @RequestMapping(value = "/api/role/role",method = RequestMethod.DELETE)
    public ResponseData deleteRole(int[] roleID){
        return role.deleteRole(roleID);
    }

    @RequiresPermissions("user:role:setPermission")
    @RequestMapping(value = "api/role/rolePermission",method = RequestMethod.POST)
    public ResponseData addRolePermission(int roleID,int permissionID){
        return role.addRolePermission(roleID,permissionID);
    }

    @RequiresPermissions("user:role:setPermission")
    @RequestMapping(value = "api/role/rolePermission",method = RequestMethod.DELETE)
    public ResponseData deleteRolePermission(int roleID,int permissionID){
        return role.deleteRolePermission(roleID,permissionID);
    }

    @RequiresPermissions("user:role:setPermission")
    @RequestMapping(value = "api/role/rolePermission",method = RequestMethod.GET)
    public ResponseData getAllPermission(){
        return role.getAllPermission();
    }
}
