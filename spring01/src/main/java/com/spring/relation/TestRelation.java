package com.spring.relation;

import com.spring.di.Car;
import com.spring.di.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRelation {

    private ApplicationContext context;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring_relation.xml");
    }

    @Test
    public void test1() {
        final Person car = context.getBean("car2", Person.class);
        System.out.println(car);
    }

    @Test
    public void test2() {
        final Person car = context.getBean("car3", Person.class);
        System.out.println(car);
    }

    @Test
    public void test3() {
        final Car car = context.getBean("car4", Car.class);
        final Car car2 = context.getBean("car4", Car.class);
        System.out.println(car == car2);
    }




}
