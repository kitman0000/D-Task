package com.dtask.DTask.userModule.dao;

import com.dtask.DTask.userModule.bo.ParentMenuBo;
import com.dtask.DTask.userModule.bo.PermissionBo;
import com.dtask.DTask.userModule.bo.RoleBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2019-12-14.
 */
@Repository
@Mapper
public interface RoleDao {
    // 获取用户角色
    RoleBo getUserRole(int userID);

    // 获取所有角色
    List<RoleBo> getAllRole();

    // 添加新角色
    void addRole(String roleName);

    // 编辑某角色名
    void editRole(@Param("id") int id, @Param("roleName") String roleName);

    // 删除角色
    void deleteRole(@Param("id") int[] id);

    // 检查某角色是否拥有某权限
    int checkRolePermission(@Param("roleID") int roleID, @Param("permissionID") int permissionID);

    // 为角色添加权限
    void addRolePermission(@Param("roleID") int roleID, @Param("permissionID") int permissionID);

    // 为角色删除权限
    void deleteRolePermission(@Param("roleID") int roleID, @Param("permissionID") int permissionID);

    //获取某角色拥有的权限-
    List<PermissionBo> getRolePermission(int roleID);

    // 获取所有权限
    List<PermissionBo> getAllPermission();


}
