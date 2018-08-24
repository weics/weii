package com.weii.pay.service.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MessageServiceApplication {
    public static void main(String[] args) {
//        if(args == null || args.length == 0) {
//            args = new String[1];
//            //	args[1] = "--spring.profiles.active=native";
//            // 需要指定配置文件名称
//            args[0]="--spring.config.name=application-boot";
//        }
        SpringApplication.run(MessageServiceApplication.class, args);
    }
}
