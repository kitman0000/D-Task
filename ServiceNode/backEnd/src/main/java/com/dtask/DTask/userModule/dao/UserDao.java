package com.dtask.DTask.userModule.dao;

import com.dtask.DTask.accountModule.bo.AccountBo;
import com.dtask.DTask.userModule.bo.UserDetailBo;
import com.dtask.DTask.userModule.bo.UserListBo;
import com.dtask.DTask.userModule.entity.UserAddEntity;
import com.dtask.DTask.userModule.entity.UserSelectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2019-12-6.
 */
@Mapper
@Repository
public interface UserDao {

    // 获取用户数量
    int getUserNumber(@Param("userSelectEntity") UserSelectEntity userSelectEntity);

    // 获取某页用户信息
    List<UserListBo> getUserList(@Param("userSelectEntity") UserSelectEntity userSelectEntity,@Param("startRow") int startRow, @Param("rowsOnePage") int rowsOnePage);

    // 获取单个用户详细信息
    UserDetailBo getUserDetail(int userID);

    // 添加用户
    void addUser(@Param("userAddEntity") UserAddEntity userAddEntity);

    // 修改用户(管理员)
    void updateUser(@Param("userAddEntity") UserAddEntity userAddEntity);

    // 批量删除用户
    void deleteUser(@Param("userID") int[] userID);
}
