package com.dtask.DTask.userModule.dao;

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
    List<RoleBo> getAllUser();

    // 添加新角色
    void addRole(String roleName);

    // 删除角色
    void deleteRole(int roleID);

    // 检查某角色是否拥有某权限
    int checkRolePermission(@Param("roleID") int roleID, @Param("permissionID") int permissionID);

    // 为角色添加权限
    void addRolePermission(@Param("roleID") int roleID, @Param("permissionID") int permissionID);

    // 为角色删除权限
    void deleteRolePermission(@Param("roleID") int roleID, @Param("permissionID") int permissionID);

}
