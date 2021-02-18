package com.dtask.pluginsdk.userModule;

/**
 * Created by zhong on 2020-4-5.
 */
public interface IRoleEvent {
    void addRole(String roleName);

    void editRole(int roleID, String roleName);

    void getRole();

    void deleteRole(int[] roleID);

    void getRolePermission(int roleID);

    void addRolePermission(int roleID, int permissionID);

    void deleteRolePermission(int roleID, int permissionID);

    void getAllPermission();

}
