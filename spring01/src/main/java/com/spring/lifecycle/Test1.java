package com.spring.lifecycle;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {

    private ConfigurableApplicationContext context;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring_lifecycle.xml");
    }


    @Test
    public void test1(){
        final Car car = context.getBean("car", Car.class);
        System.out.println(car);
        context.close();
    }
}
