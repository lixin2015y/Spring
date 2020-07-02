package com.lee.test;

import com.lee.bean.MainConfigValue;
import com.lee.entity.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestPropertyValue {

    private void printAllBeans(AnnotationConfigApplicationContext context){
        final String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void test() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigValue.class);
        printAllBeans(context);
        final Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }

}
