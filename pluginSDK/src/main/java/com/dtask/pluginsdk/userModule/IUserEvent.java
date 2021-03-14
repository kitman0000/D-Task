package com.dtask.pluginsdk.userModule;

/**
 * Created by zhong on 2020-3-24.
 */
public interface IUserEvent {
    void getUserOwnDetail();

    void changeUserDetail(int id,String username,String nickname,
                          String phone,String email,int roleID,int departmentID,String birthday,String onboardDate);

    void addUser(String username,String nickname,
                 String phone,String email,int roleID,int departmentID,String birthday,String onboardDate);

    void editUser(int id,String username,String nickname,
                  String phone,String email,int roleID,int departmentID,String birthday,String onboardDate);

    void deleteUser(int[] userID);
}
