package com.MQClouder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * Created by zhong on 2020-5-12.
 */

@Component
public class RabbitSender implements RabbitTemplate.ConfirmCallback, ReturnCallback{

    protected String exchangeName = "topicExchange";

    private Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    @Autowired
    protected RabbitTemplate rabbitTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("Message send success:",correlationData);
        } else {
            logger.error("Message send failed:",correlationData,cause);
        }

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.error("Message send failed:",message.getBody(),replyCode);
    }

    protected String send(String routingKey,String msg){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        return rabbitTemplate.convertSendAndReceive("topicExchange", routingKey, msg, correlationId).toString();
    }

    protected void sendWithoutResponse(String routingKey,String msg){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(routingKey,msg);
    }
}
