package com.dtask.pluginsdk.userModule;

/**
 * Created by zhong on 2020-5-2.
 */
public interface IDepartmentEvent {
    void addDepartment(String name);

    void editDepartment(int id, String name);

    void deleteDepartment(int id);

    void getDepartment();
}
