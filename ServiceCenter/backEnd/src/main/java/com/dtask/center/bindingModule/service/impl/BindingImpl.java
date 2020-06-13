package com.dtask.center.bindingModule.service.impl;

import com.dtask.center.bindingModule.bo.BindingRequestBo;
import com.dtask.center.bindingModule.bo.NodeBo;
import com.dtask.center.bindingModule.dao.BindingDao;
import com.dtask.center.bindingModule.entity.AskBindingEntity;
import com.dtask.center.bindingModule.entity.HandleBindingEntity;
import com.dtask.center.bindingModule.entity.NodeEntity;
import com.dtask.center.bindingModule.service.IBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-5-16.
 */
@Service
public class BindingImpl implements IBinding {
    @Autowired
    private BindingDao bindingDao;

    @Override
    public String askBanding(AskBindingEntity askBindingEntity) {

        // 先判断是否已经有绑定
        String inheritRp = bindingDao.getNodeInheritRpByNodeID(askBindingEntity.getNodeID());
        if(inheritRp != null){
            return "BIND_FAILED_1";
        }

        // 若无绑定，则查找是否有为null的请求
        // 此时若有为true的请求，只是已解绑的请求
        int intResult = bindingDao.getUnhandleRequest(askBindingEntity.getNodeID());
        if(intResult != 0){
            return "BIND_FAILED_2";
        }

        bindingDao.addBindingRequest(askBindingEntity.getNodeID(), askBindingEntity.getRequestBindID());
        return "BIND_SUCCESS";
    }

    @Override
    public void addNode(NodeEntity nodeEntity) {
        bindingDao.insertNode(nodeEntity.getNodeName());
    }

    @Override
    public String handleBinding(HandleBindingEntity handleBindingEntity) {
        bindingDao.updateBindingRequest(handleBindingEntity.getRequestID(),handleBindingEntity.isAccept());
        if(!handleBindingEntity.isAccept()) {
            return "HANDLE_SUCCESS";
        }
        BindingRequestBo requestBo = bindingDao.getRequest(handleBindingEntity.getRequestID());
        bindingDao.updateBindingRp(requestBo.getRequestNodeID(),requestBo.getRequestBindID());
        return "HANDLE_SUCCESS";
    }

    @Override
    public int getNodeID(String nodeName) {
        return bindingDao.getNodeID(nodeName);
    }

    @Override
    public List<NodeBo> getAllNodes() {
        return bindingDao.getAllNodes();
    }

    @Override
    public String unbind(NodeEntity nodeEntity) {
        String nodeInheritRp = bindingDao.getNodeInheritRpByNodeID(nodeEntity.getNodeID());
        if(nodeInheritRp == null)
            return "UNBIND_FAILED";
        bindingDao.unbindNode(nodeInheritRp,null);
        return "UNBIND_SUCCESS";
    }

    @Override
    public List<BindingRequestBo> getBindRequest(NodeEntity nodeEntity) {
        return bindingDao.getNodeBindRequest(nodeEntity.getNodeID());
    }

    @Override
    public String setRoot(NodeEntity nodeEntity) {
        if(bindingDao.checkRoot() == 1){
            return "ROOT_EXIST";
        }
        bindingDao.setRoot(nodeEntity.getNodeID());

        return "SET_SUCCESS";
    }
}
