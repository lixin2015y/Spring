package com.lee.spi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Dog implements Animal {
    @Override
    public void sayHello() {
        System.out.println("汪汪汪");
    }
}
