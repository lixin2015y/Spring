package com.spring.di;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDi {

    @Test
    public void testProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_di.xml");
        final Car car = context.getBean("car", Car.class);
        System.out.println(car);
    }

    @Test
    public void testContructer() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_di.xml");
        final Car car = context.getBean("car2", Car.class);
        System.out.println(car);

    }

    @Test
    public void testContructer2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_di.xml");
        final Car car = context.getBean("car3", Car.class);
        System.out.println(car);

    }

    @Test
    public void testContructer3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_di.xml");
        final Car car = context.getBean("car4", Car.class);
        System.out.println(car);
    }

    @Test
    public void testContructer4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_di.xml");
        final Car car = context.getBean("car5", Car.class);
        System.out.println(car);
    }


    @Test
    public void testContructer5() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_di.xml");
        final Car car = context.getBean("car6", Car.class);
        System.out.println(car);
    }

    @Test
    public void testContructer6() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_di.xml");
        final Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }

    private ApplicationContext context;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring_di.xml");
    }

    @Test
    public void testContructer7() {
        final Person person = context.getBean("person1", Person.class);
        System.out.println(person);
    }

    @Test
    public void testContructer8() {
        final Person person = context.getBean("person2", Person.class);
        System.out.println(person);
    }

    @Test
    public void testContructer9() {
        final Person person = context.getBean("person3", Person.class);
        System.out.println(person);
    }

    @Test
    public void testContructer10() {
        final Person person = context.getBean("person4", Person.class);
        System.out.println(person);
    }

    @Test
    public void testContructer11() {
        final Person person = context.getBean("person5", Person.class);
        System.out.println(person);
    }

    @Test
    public void testContructer12() {
        final Person person = context.getBean("person6", Person.class);
        System.out.println(person);
    }
}
