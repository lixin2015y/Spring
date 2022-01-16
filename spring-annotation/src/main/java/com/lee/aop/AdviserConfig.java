package com.lee.aop;

import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdviserConfig {

    @Bean
    NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor() {
        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setAdvice(new MethodBeforeAdviser());
        nameMatchMethodPointcutAdvisor.setMappedName("div");
        return nameMatchMethodPointcutAdvisor;
    }
}
