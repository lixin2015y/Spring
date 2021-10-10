package com.lee.eventListen;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;



@Component
public class MyListener implements ApplicationListener<ApplicationEvent> {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("监听到事件" + event.toString());
    }
}
