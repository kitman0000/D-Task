package com.dtask.DTask.bindingModule.controller;

import com.dtask.DTask.bindingModule.service.IBinding;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-6-14.
 */

@RestController
public class BindingCl {
    @Autowired
    IBinding binding;

    public ResponseData getAllNodes() {
        return binding.getAllNodes();
    }

    public ResponseData askBinding(int requestBindID) {
        return binding.askBinding(requestBindID);
    }

    public ResponseData getBindRequest() {
        return binding.getBindRequest();
    }

    public ResponseData handleBinding(int request, boolean accept) {
        return binding.handleBindingRequest(request,accept);
    }

    public ResponseData setRoot() {
        return binding.setRoot();
    }

    public ResponseData unbind() {
        return binding.unbind();
    }

    /** 以下为自动操作 **/

    public ResponseData addNode() {
        return binding.addNode();
    }

    public ResponseData getNodeID() {
        return binding.getNodeID();
    }
}
