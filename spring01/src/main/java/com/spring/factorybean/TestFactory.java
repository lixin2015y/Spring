package com.spring.factorybean;

import com.spring.di.Car;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactory {

    private ApplicationContext context;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring_factorybean.xml");
    }

    @Test
    public void test1() {
        final Car car = context.getBean("car", Car.class);
        System.out.println(car);
    }



}
