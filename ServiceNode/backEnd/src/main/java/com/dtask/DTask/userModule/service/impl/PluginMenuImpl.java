package com.dtask.DTask.userModule.service.impl;

import com.dtask.pluginsdk.userModule.IMenuEvent;
import com.dtask.pluginsdk.userModule.bo.ChildMenuBo;
import com.dtask.pluginsdk.userModule.bo.ParentMenuBo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhong on 2021-2-17.
 */
@Component
public class PluginMenuImpl implements IMenuEvent {
    @Override
    public void getUserMenu(List<ParentMenuBo> parentMenuBos) {
        ParentMenuBo parentMenu = new ParentMenuBo();

        parentMenu.setName("插件");

        ChildMenuBo childMenuBo = new ChildMenuBo();
        childMenuBo.setName("插件菜单");
        childMenuBo.setUrl("pluginPage?pageUrl=HelloWorld");
        childMenuBo.setIcon("chajian.png");

        List<ChildMenuBo> childMenuBos = new LinkedList<>();
        childMenuBos.add(childMenuBo);
        parentMenu.setChildMenuBoList(childMenuBos);

        parentMenuBos.add(parentMenu);

    }
}
