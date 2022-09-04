package com.compensate.config;


import com.compensate.advisor.CompensationAspect;
import com.compensate.postProcessor.CompensateRegisterBeanPostProcessor;
import com.compensate.supports.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;


@Configuration
@Order(Integer.MIN_VALUE)
public class CompensationConfiguration {


    @Bean
    public CompensationAspect aspect() {
        return new CompensationAspect();
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @DependsOn({"springContextHolder"})
    public CompensateRegisterBeanPostProcessor compensationPostProcessor() {
        return new CompensateRegisterBeanPostProcessor();
    }

}
