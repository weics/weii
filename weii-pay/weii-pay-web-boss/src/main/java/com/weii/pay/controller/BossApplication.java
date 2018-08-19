package com.weii.pay.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @Author: weics
 * @Date: Created in 22:11 2018/8/19
 * @Description:
 * @Modified By:
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class BossApplication {
    public static void main(String[] args) {
        SpringApplication.run(BossApplication.class, args);
    }
}
