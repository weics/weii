package com.weii.pay.app.notify.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: weics
 * @Date: Created in 14:53 2018/5/13
 * @Description:
 * @Modified By:
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue("hello");
    }
}
