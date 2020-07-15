package com.lee.ext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtConfig {

    @Bean(initMethod = "init")
    public Blue blue(){
        return new Blue();
    }

    @Bean
    MyBeanFactoryPostprocessor myBeanPostProcessor() {
        return new MyBeanFactoryPostprocessor();
    }

    @Bean
    MyBeanDefinitionRegistryPostprocessor myBeanDefinitionRegistryPostprocessor(){
        return new MyBeanDefinitionRegistryPostprocessor();
    }
}
