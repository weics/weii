package com.weii.pay.service.message.api.config;

import com.weii.pay.service.message.entity.RpTransactionMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: weics
 * @Date: Created in 15:11 2018/5/13
 * @Description:
 * @Modified By:
 */
@Component
public class RabbitSender implements RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入
     */
    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    public void sendMsg(RpTransactionMessage content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY, content, correlationId);
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息成功消费");
        } else {
            System.out.println("消息消费失败:" + cause);
        }
    }

}