package com.dtask.DTask.userModule.dao;

import com.dtask.DTask.userModule.bo.ChildMenuBo;
import com.dtask.DTask.userModule.bo.ParentMenuBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-3-22.
 */
@Repository
@Mapper
public interface MenuDao {
    // 获取某角色所有菜单
    List<ParentMenuBo> getRoleMenu(int roleID);

    // 获取某父菜单的子菜单
    List<ChildMenuBo> getChildMenu(int parentMenuID);

    // 获取某角色拥有的某父菜单的子菜单
    List<ChildMenuBo> getRoleChildMenu(@Param("roleID") int roleID, @Param("parentMenuID") int parentMenuID);
}
