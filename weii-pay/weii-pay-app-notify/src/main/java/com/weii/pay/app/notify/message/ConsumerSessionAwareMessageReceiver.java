package com.weii.pay.app.notify.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: weics
 * @Date: Created in 15:00 2018/5/13
 * @Description:
 * @Modified By:
 */
@Component
@RabbitListener(queues = "hello")
public class ConsumerSessionAwareMessageReceiver {


    @RabbitHandler
    public void process(Message hello) {
        System.out.println("Receiver  : " + hello);
    }
}
