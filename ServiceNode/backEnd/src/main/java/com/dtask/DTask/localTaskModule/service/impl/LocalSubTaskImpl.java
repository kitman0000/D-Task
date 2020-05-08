package com.dtask.DTask.localTaskModule.service.impl;

import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.dao.LocalSubTaskDao;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.DTask.localTaskModule.service.ILocalSubTask;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.util.PageDivideUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@Service
public class LocalSubTaskImpl implements ILocalSubTask{

    private final int COUNT_ONE_PAGE = 10;

    @Autowired
    LocalSubTaskDao localSubTaskDao;

    @Override
    public ResponseData addLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {
        localSubTaskDao.addLocalSubTask(localSubTaskEntity);
        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData editLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {
        localSubTaskDao.updateLocalSubTask(localSubTaskEntity);
        return new ResponseData(1,"修改成功",null);
    }

    @Override
    public ResponseData deleteLocalSubTask(int[] id) {
        localSubTaskDao.deleteLocalSubTask(id);
        return new ResponseData(1,"删除成功",null);
    }

    @Override
    public ResponseData getLocalSubTaskNumber(LocalSubTaskEntity localSubTaskEntity) {
        int count = localSubTaskDao.getLocalSubTaskNumber(localSubTaskEntity.getTaskID());
        int page = PageDivideUtil.getCountOfPages(count,COUNT_ONE_PAGE);
        return new ResponseData(page,"查询成功",null);
    }

    @Override
    public ResponseData getLocalSubTaskList(LocalSubTaskEntity localSubTaskEntity) {
        List<LocalSubTaskBo> localSubTaskBoList = localSubTaskDao.getLocalSubTaskList(localSubTaskEntity.getTaskID());
        return new ResponseData(1,"查询成功",localSubTaskBoList);
    }


    @Override
    public ResponseData editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity) {

        int userID = UserCommon.getUserBo().getUserID();
        int subTaskID = localSubTaskEntity.getId();

        // 判断是否是管理员
        if(!localSubTaskDao.isUserAdmin(userID) || !localSubTaskDao.isAllowUserChangeState(subTaskID)){
            return new ResponseData(2,"权限不足",null);
        }

        localSubTaskDao.updateLocalSubTaskStatus(localSubTaskEntity);

        return new ResponseData(1,"修改成功",null);

    }


}
