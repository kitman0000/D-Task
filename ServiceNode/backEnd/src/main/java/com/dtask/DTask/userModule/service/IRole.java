package com.dtask.DTask.userModule.service;

import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-4-5.
 */
public interface IRole {
    ResponseData addRole(String roleName);

    ResponseData editRole(int roleID,String roleName);

    ResponseData getRole();

    ResponseData deleteRole(int[] roleID);

    ResponseData addRolePermission(int roleID, int permissionID);

    ResponseData deleteRolePermission(int roleID,int permissionID);

    ResponseData getAllPermission();
}
