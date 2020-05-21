package com.dtask.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Created by zhong on 2020-5-12.
 */
@Configuration
public class RabbitConfig {

    //bindingAsk
    @Bean
    public Queue bindingAskQueue() {
        return new Queue("dtask.binding.ask", true);
    }

    @Bean
    public Binding bindBindingAskQueue(){
        return BindingBuilder.bind(bindingAskQueue()).to(topicExchange()).with("dtask.binding.ask");
    }

    //bindingHandleB
    @Bean
    public Queue bindingHandleQueue() {
        return new Queue("dtask.binding.handle", true);
    }

    @Bean
    public Binding bindindingHandleQueue() {
        return BindingBuilder.bind(bindingHandleQueue()).to(topicExchange()).with("dtask.binding.handle");
    }

    //getNodeID
    @Bean
    public Queue getNodeID() {
        return new Queue("dtask.getNodeID", true);
    }

    @Bean
    public Binding bindingGetNodeID() {
        return BindingBuilder.bind(getNodeID()).to(topicExchange()).with("dtask.getNodeID");
    }

    //addNode
    @Bean
    public Queue addNodeQueue() {
        return new Queue("dtask.addNode", true);
    }

    @Bean
    public Binding bindingAddNodeQueue(){
        return BindingBuilder.bind(addNodeQueue()).to(topicExchange()).with("dtask.addNode");
    }

    //getAllNodes
    @Bean
    public Queue getAllNodes() {
        return new Queue("dtask.getAllNodes", true);
    }

    @Bean
    public Binding bindGetAllNodes(){
        return BindingBuilder.bind(getAllNodes()).to(topicExchange()).with("dtask.getAllNodes");
    }

    // unbind
    @Bean
    public Queue unbind() {
        return new Queue("dtask.binding.unbind", true);
    }

    @Bean
    public Binding bindUnbind(){
        return BindingBuilder.bind(unbind()).to(topicExchange()).with("dtask.binding.unbind");
    }

    @Bean
    public Queue getBindRequest(){
        return new Queue("dtask.binding.getBindRequest", true);
    }
    @Bean
    public Binding bindGetBindRequest(){
        return BindingBuilder.bind(getBindRequest()).to(topicExchange()).with("dtask.binding.getBindRequest");
    }

    //声明交换器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

}