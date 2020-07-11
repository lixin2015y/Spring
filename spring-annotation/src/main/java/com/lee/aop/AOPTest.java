package com.lee.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest {


    @Test
    public void test() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
//        final Calculator calculator = context.getBean(Calculator.class);
//        System.out.println(calculator.div(1, 1));

//        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    }
}
