package com.dtask.DTask.accountModule.dao;

import com.dtask.DTask.accountModule.bo.AccountBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2019-12-5.
 */
@Mapper
@Repository
public interface AccountDao {
    AccountBo findUserIDByName(@Param("username") String username);

    String getUserPwd(int userID);

    void updateUserPwd(@Param("id") int userID, @Param("pwd") String pwd);

    void addAccount(@Param("username") String username, @Param("pwd") String pwd);
}
