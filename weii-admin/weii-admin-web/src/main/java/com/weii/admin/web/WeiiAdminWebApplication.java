package com.weii.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: weics
 * @Date: Created in 22:07 2018/06/28
 * @Description:
 * @Modified By:
 */
@SpringBootApplication(scanBasePackages = "com.weii.admin")
public class WeiiAdminWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeiiAdminWebApplication.class,args);
    }
}