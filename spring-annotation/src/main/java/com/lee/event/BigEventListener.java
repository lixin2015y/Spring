package com.lee.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @className: HelloEventListener
 * @author: li xin
 * @date: 2022-01-23
 **/
@Component
public class BigEventListener implements ApplicationListener<BigEvent> {
    @Override
    public void onApplicationEvent(BigEvent event) {
        System.out.println("监听到bigEvent事件，当前name=" + event.getName());
    }


    @EventListener(BigEvent.class)
    public void listener(BigEvent event) {
        System.out.println("使用注解的监听器。。。。监听到bigEvent事件，当前name=" + event.getName());
    }
}
