package com.dtask.pluginsdk.bindingModule;

public interface INodeProvider {

    /**
     * 获取当前节点ID
     */
    int getNodeID();

    /**
     * 获取当前节点名称
     */
    String getNodeName();
}
