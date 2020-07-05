package com.lee.bean;

import com.lee.component.Car;
import com.lee.component.MyBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 容器管理生命周期，可以自定义初始化或者销毁方法
 */
@Import(MyBeanPostProcessor.class)
@Configuration
public class MyBeanLifeCycle {


    @Bean
    public Car car(){
        return new Car();
    }


}
