package com.dtask.pluginsdk.bindingModule;

/**
 * Created by zhong on 2020-6-14.
 */
public interface IBindingEvent {

    void askBinding(int requestBindID);

    void handleBindingRequest(int request, boolean accept);

    void setRoot();

    void unbind();
}
