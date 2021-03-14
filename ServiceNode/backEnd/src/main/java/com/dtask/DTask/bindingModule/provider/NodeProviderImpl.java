package com.dtask.DTask.bindingModule.provider;

import com.dtask.common.NodeCommon;
import com.dtask.pluginsdk.bindingModule.INodeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NodeProviderImpl implements INodeProvider {

    @Value("${nodeName}")
    private String nodeName;

    @Autowired
    NodeCommon nodeCommon;

    @Override
    public int getNodeID() {
        return nodeCommon.getNodeID();
    }

    @Override
    public String getNodeName() {
        return nodeName;
    }
}
