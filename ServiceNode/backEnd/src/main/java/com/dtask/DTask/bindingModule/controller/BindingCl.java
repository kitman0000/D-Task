package com.dtask.DTask.bindingModule.controller;

import com.dtask.DTask.bindingModule.service.IBinding;
import com.dtask.common.ResponseData;
import com.dtask.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-6-14.
 */
@EnableScheduling
@RestController
public class BindingCl {
    @Autowired
    IBinding binding;

    @RequestMapping(value = "/api/bindingCl/allNodes",method = RequestMethod.GET)
    public ResponseData getAllNodes() {
        return binding.getAllNodes();
    }

    @RequestMapping(value = "/api/bindingCl/askBinding",method = RequestMethod.POST)
    public ResponseData askBinding(int requestBindID) {
        return binding.askBinding(requestBindID);
    }

    @RequestMapping(value = "/api/bindingCl/bindRequest",method = RequestMethod.GET)
    public ResponseData getBindRequest() {
        return binding.getBindRequest();
    }

    @RequestMapping(value = "/api/bindingCl/handleBinding",method = RequestMethod.POST)
    public ResponseData handleBinding(int request, boolean accept) {
        return binding.handleBindingRequest(request,accept);
    }

    @RequestMapping(value = "/api/bindingCl/setRoot",method = RequestMethod.POST)
    public ResponseData setRoot() {
        return binding.setRoot();
    }

    @RequestMapping(value = "/api/bindingCl/unbind",method = RequestMethod.POST)
    public ResponseData unbind() {
        return binding.unbind();
    }

    @RequestMapping(value = "/api/bindingCl/nodeID",method = RequestMethod.GET)
    public ResponseData apiGetNodeID(){
        return binding.apiGetNodeID();
    }

    @Scheduled(cron = "0/30 * * * * ? ")
    public void sendKeepAlive(){
        binding.sendKeepAlive();
    }
}
