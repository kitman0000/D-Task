package com.dtask.DTask.accountModule.dao;

import com.dtask.DTask.userModule.bo.UserBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2019-12-5.
 */
@Mapper
@Repository
public interface AccountDao {
    UserBo findUserIDByName(@Param("username") String username);

    String getUserPwd(int userID);

    void updateUserPwd(@Param("userID") int userID, @Param("pwd") String pwd);
}
