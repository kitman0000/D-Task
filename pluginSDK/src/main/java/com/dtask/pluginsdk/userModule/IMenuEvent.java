package com.dtask.pluginsdk.userModule;

import com.dtask.pluginsdk.userModule.bo.ParentMenuBo;

import java.util.List;

/**
 * Created by zhong on 2020-3-20.
 */
public interface IMenuEvent {
    void getUserMenu(List<ParentMenuBo> parentMenuBos);
}
