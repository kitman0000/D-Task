package com.dtask.pluginsdk.localTaskModule;

/**
 * Created by zhong on 2020-5-3.
 */
public interface ILocalTaskEvent {
    void addLocalTask(String name);

    void editLocalTask(int id, String name, int creator, boolean allowedMemberChangeStatus);

    void deleteLocalTask(int id);

    void addLocalTaskMember(int taskID, int userID);

    void removeLocalTaskMember(int taskID, int userID);

    void toggleTaskAdmin(int taskID, int userID, boolean isAdmin);
}
