package com.lee.ioc;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener {

    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("实现接口监听到event" + event);
    }

    @EventListener
    public void listen(ApplicationEvent event) {
        System.out.println("使用@EventListener监听到" + event);
    }
}
