package com.dtask.pluginsdk.accountModule;

/**
 * Created by zhong on 2020-3-12.
 */
public interface IAccountEvent {
    void login(String username);

    void addAccount(String username) throws Exception;

    void updatePwd(int id);

    void deleteUser(int[] id);

    void remoteLogin(String username,int userNodeID,int loginNodeID);

    void mqRemoteLogin(String username);

}
