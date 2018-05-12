package com.weii.pay.app.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;

/**
 * @Author: weics
 * @Date: Created in 22:46 2018/5/10
 * @Description: 消息处理定时器
 * 主要分为两步： MessageStatusEnum
 * 1.处理状态为“待确认”但已超时的消息
 * 2.处理状态为“发送中”但超时没有被成功消费确认的消息
 * @Modified By:
 */
public class MessageTask implements CommandLineRunner {

    private static final Log log = LogFactory.getLog(MessageTask.class);

    private MessageTask() {

    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
