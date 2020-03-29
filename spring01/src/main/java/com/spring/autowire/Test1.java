package com.spring.autowire;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {

    ConfigurableApplicationContext context;

    @Before
    public void test1() {
        context = new ClassPathXmlApplicationContext("spring_autowire.xml");
    }

    @Test
    public void test2() {
        final Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }
}
