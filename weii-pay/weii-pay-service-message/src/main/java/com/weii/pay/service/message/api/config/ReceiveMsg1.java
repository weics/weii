package com.weii.pay.service.message.api.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues ="hello")
public class ReceiveMsg1 {
    /**
     * 获取信息:
     *  queue也可以支持RabbitMQ中对队列的模糊匹配
     * @param hello
     */
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1111111111:" + hello);
    }
}
