package com.weii.admin.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: weics
 * @Date: Created in 21:52 2018/7/14
 * @Description:
 * @Modified By:
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
@MapperScan("com.weii.admin.dao.mapper")
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.weii.admin.dao.mapper");
        return mapperScannerConfigurer;
    }

}
