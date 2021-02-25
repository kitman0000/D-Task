package com.dtask.center.accountModule.service.impl;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.center.accountModule.entity.RemoteLoginEntity;
import com.dtask.center.accountModule.service.IAccount;
import com.dtask.center.bindingModule.bo.NodeBo;
import com.dtask.center.bindingModule.dao.BindingDao;
import com.dtask.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhong on 2020-7-10.
 */
@Service
public class AccountImpl implements IAccount {

    @Autowired
    private EncryptRabbitSender rabbitSender;

    @Autowired
    BindingDao bindingDao;

    @Override
    public String remoteLogin(String msg) {

        // 解析数据
        RemoteLoginEntity remoteLoginEntity = (RemoteLoginEntity) JsonUtil.jsonToObject(msg,RemoteLoginEntity.class);

        // 确认所登录的节点是用户所在节点的子节点
        int userNodeID = remoteLoginEntity.getUserNodeID();
        int loginNodeID = remoteLoginEntity.getLoginNodeID();

        String inheritRp = bindingDao.getNodeInheritRpByNodeID(loginNodeID);

        // 继承关系为 root:aaa:bbb:ccc 即可认为bbb 与 aaa为ccc的上级节点
        if(!inheritRp.contains(":" + String.valueOf(userNodeID) + ":")){
            // 判断是否为根节点的用户
            if(bindingDao.getRoot().getId() != userNodeID){
                return "SYS_FAILED";
            }
        }

        // 从接受的消息中，获取节点名称，并向routingKey 发送访问登录，并返回给原节点
        String nodeName = bindingDao.getNodeName(remoteLoginEntity.getUserNodeID());
        return rabbitSender.encryptSend("dtask.account.login." + nodeName,msg);
    }

    @Override
    public String getParentNodes(int nodeID) {
        String inheritRp = bindingDao.getNodeInheritRpByNodeID(nodeID);

        // 分割用:连接的节点ID
        String[] nodeIDArray = inheritRp.split(":");

        // 将节点ID和节点名放入nodeBoList
        List<NodeBo> nodeBoList = new LinkedList<>();
        for (String node : nodeIDArray){
            if(node.equals("root")){
                // 如果是根节点，获取根节点的ID和名称
                NodeBo nodeBo = bindingDao.getRoot();
                if(nodeBo.getId() != nodeID) {
                    // 避免加入自身
                    nodeBoList.add(nodeBo);
                }
            }else {
                // 如果不是根节点，获取该节点的ID和名称
                NodeBo nodeBo = new NodeBo();
                String nodeName = bindingDao.getNodeName(Integer.valueOf(node));
                nodeBo.setId(Integer.valueOf(node));
                nodeBo.setNodeName(nodeName);

                if(nodeBo.getId() != nodeID) {
                    // 避免加入自身
                    nodeBoList.add(nodeBo);
                }
            }
        }

        return JsonUtil.objectToJson(nodeBoList);
    }
}
