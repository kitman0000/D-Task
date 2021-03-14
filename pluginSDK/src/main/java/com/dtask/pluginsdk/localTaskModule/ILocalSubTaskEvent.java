package com.dtask.pluginsdk.localTaskModule;

/**
 * Created by zhong on 2020-5-3.
 */
public interface ILocalSubTaskEvent {
    void addLocalSubTask(int taskID,String name, String content, String deadline, int status, String startTime,
                         int level, String tag, int star);

    void editLocalSubTask(int taskID, int id, String name, String content, String deadline, int status, String startTime,
                          int level, String tag, int star);

    void deleteLocalSubTask(int taskID, int[] id);

    void editLocalSubTaskStatus(int taskID,int id,int status);
}
