package com.lee.eventListen;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MyAnnotationListener {


    @EventListener(classes = ApplicationEvent.class)
    public void listen(ApplicationEvent applicationEvent) {
        System.out.println("使用注解完成事件" + applicationEvent.toString());
    }
}
