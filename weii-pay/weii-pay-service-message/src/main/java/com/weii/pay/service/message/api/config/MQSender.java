package com.weii.pay.service.message.api.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: weics
 * @Date: Created in 15:11 2018/5/13
 * @Description:
 * @Modified By:
 */
@Component
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendQueue(String exchangeKey, String queueKey, Object object) {
        amqpTemplate.convertAndSend(exchangeKey, queueKey, object);
    }

    public void sendQueue(String queueKey, Object object) {
        amqpTemplate.convertAndSend(queueKey, object);
    }


}