package com.weii.admin.web.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;


/**
 * @Author: weics
 * @Date: Created in 21:49 2018/06/24
 * @Description:
 * @Modified By:
 */
@Configurable
public class ValidatorConfig {
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        final MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
//        // 设置 validator 模式为快速失败返回
//        postProcessor.setValidator(this.validatorFailFast());
//        return postProcessor;
//        // 默认是普通模式，会返回所有的验证不通过信息集合
//        // return new MethodValidationPostProcessor();
//    }
//
//    @Bean
//    public Validator validatorFailFast() {
//        final ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//                .configure()
//                .addProperty("hibernate.validator.fail_fast", "true")
//                .buildValidatorFactory();
//        return validatorFactory.getValidator();
//    }
}
