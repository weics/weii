package com.weii;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: weics
 * @Date: Created in 22:07 2018/06/28
 * @Description:
 * @Modified By:
 */

@SpringBootApplication(exclude ={ DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
@ComponentScan(basePackages={"com.weii.admin.web","com.weii.admin.service"})
@MapperScan("com.weii.admin.dao.mapper")
public class WeiiAdminWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeiiAdminWebApplication.class,args);
    }
}
