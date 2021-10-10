package com.lee.eventListen;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ListenerTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        ApplicationEvent applicationEvent = new ApplicationEvent("我发布了事件"){};
        context.publishEvent(applicationEvent);
    }
}
