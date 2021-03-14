package com.dtask.center.bindingModule.service;

import com.dtask.center.bindingModule.bo.BindingRequestBo;
import com.dtask.center.bindingModule.bo.NodeBo;
import com.dtask.center.bindingModule.entity.AskBindingEntity;
import com.dtask.center.bindingModule.entity.HandleBindingEntity;
import com.dtask.center.bindingModule.entity.NodeEntity;

import java.util.List;

/**
 * Created by zhong on 2020-5-16.
 */
public interface IBinding {

    String askBanding(AskBindingEntity askBindingEntity);

    void addNode(NodeEntity nodeEntity);

    String handleBinding(HandleBindingEntity handleBindingEntity);

    int getNodeID(String nodeName);

    List<NodeBo> getAllNodes();

    String unbind(NodeEntity nodeEntity);

    List<BindingRequestBo> getBindRequest(NodeEntity nodeEntity);

    String setRoot(NodeEntity nodeEntity);

    void receiveKeepAlive(int nodeID);
}
