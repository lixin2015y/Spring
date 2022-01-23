package com.lee.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: EventTest
 * @author: li xin
 * @date: 2022-01-23
 **/
public class EventTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        context.publishEvent(new BigEvent(context, "li xin"));
    }
}
