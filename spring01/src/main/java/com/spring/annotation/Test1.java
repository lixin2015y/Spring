package com.spring.annotation;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {


    @Test
    public void test1() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring_annotation.xml");
        final UserController userController = context.getBean("userController", UserController.class);
        System.out.println(userController);
        final UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
    }
}
