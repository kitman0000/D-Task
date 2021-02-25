package com.dtask.liveMeeting.liveMeetingModule.service;

import com.dtask.pluginsdk.userModule.IMenuEvent;
import com.dtask.pluginsdk.userModule.bo.ChildMenuBo;
import com.dtask.pluginsdk.userModule.bo.ParentMenuBo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhong on 2021-2-18.
 */
@Component
public class MenuEventImpl implements IMenuEvent {
    @Override
    public void getUserMenu(List<ParentMenuBo> list) {
        ParentMenuBo parentMenu = new ParentMenuBo();

        parentMenu.setName("插件");
        parentMenu.setId(parentMenu.hashCode());
        ChildMenuBo childMenuBo = new ChildMenuBo();
        childMenuBo.setName("插件菜单");
        childMenuBo.setUrl("pluginPage?pageUrl=pluginPage/liveMeeting/liveMeeting");
        childMenuBo.setIcon("chajian.png");
        childMenuBo.setId(childMenuBo.hashCode());

        List<ChildMenuBo> childMenuBos = new LinkedList<>();
        childMenuBos.add(childMenuBo);
        parentMenu.setChildMenuBoList(childMenuBos);

        list.add(parentMenu);
    }
}
