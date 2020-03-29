package com.aspectj.annotation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {

    ClassPathXmlApplicationContext context;

    @Before
    public void test() {
        context = new ClassPathXmlApplicationContext("springContexApplicationAnnotation.xml");
    }


    @Test
    public void test2() {
        final Calculator calculator = context.getBean("calculatorImpl", Calculator.class);
        final int add = calculator.add(1, 1);
        System.out.println(add);
    }
}
