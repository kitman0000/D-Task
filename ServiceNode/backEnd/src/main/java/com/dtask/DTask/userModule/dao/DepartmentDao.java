package com.dtask.DTask.userModule.dao;

import com.dtask.DTask.userModule.bo.DepartmentBo;
import com.dtask.common.ResponseData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-5-2.
 */
@Mapper
@Repository
public interface DepartmentDao {
    void addDepartment(@Param("name") String name);

    void updateDepartment(@Param("id") int id, @Param("name") String name);

    void deleteDepartment(int id);

    List<DepartmentBo> getDepartment();
}
