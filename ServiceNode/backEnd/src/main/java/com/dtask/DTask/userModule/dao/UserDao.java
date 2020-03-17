package com.dtask.DTask.userModule.dao;

import com.dtask.DTask.accountModule.bo.UserBo;
import com.dtask.DTask.userModule.bo.UserDetailBo;
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
    int getUserNumber(@Param("username") String username, @Param("status") int status);

    // 获取某页用户信息
    List<UserBo> getUser(@Param("username") String username, @Param("status") int status,
                         @Param("startRow") int startRow, @Param("rowsOnePage") int rowsOnePage);

    // 获取单个用户详细信息
    UserDetailBo getUserDetail(int userID);

    // 删除单个用户(注销账号)
    void deleteUser(int userID);
}
