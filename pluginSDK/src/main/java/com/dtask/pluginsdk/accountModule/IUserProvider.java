package com.dtask.pluginsdk.accountModule;

public interface IUserProvider {

    /**
     * 根据header中的token获取用户ID
     * @return 用户ID
     */
    int getUserID();
}
