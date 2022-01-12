package com.lee.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest {
    @Test
    public void test() {
        final ApplicationContext context = new AnnotationConfigApplicationContext(IOCConfig.class);
        Person person = context.getBean(Person.class);
        System.out.println(person);
    }

}
