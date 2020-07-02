package com.lee.bean;

import com.lee.component.Car;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 容器管理生命周期，可以自定义初始化或者销毁方法
 */
@ComponentScan(value = "com.lee.component")
@Configuration
public class MyBeanLifeCycle {


    public Car car(){
        return new Car();
    }



}
