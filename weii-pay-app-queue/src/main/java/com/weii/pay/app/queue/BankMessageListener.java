package com.weii.pay.app.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 18:44 2018/5/11
 * @Description:
 */
public class BankMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

    }
}
