package com.lee.component;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {


    public Dog() {
        System.out.println("构造方法");
    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("调用Dog启动方法");
    }

    @PreDestroy
    public void destroy() throws Exception {
        System.out.println("调用Dog销毁方法");
    }
}
