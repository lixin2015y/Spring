package com.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    public static void main(String[] args) {


        // 1、 创建springIOC对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringContext.xml");

        // 2、获取person对象
        final Person person = (Person) applicationContext.getBean("person");

        System.out.println(person);
    }
}
