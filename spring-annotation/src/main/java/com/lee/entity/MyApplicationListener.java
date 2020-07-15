package com.lee.entity;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class MyApplicationListener implements ApplicationListener {

    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("调用我实现了ApplicationListener接口的的onApplicationEvent方法。。。。。。");
    }
}
