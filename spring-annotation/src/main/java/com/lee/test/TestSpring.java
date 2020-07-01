package com.lee.test;

import com.lee.bean.MainConfig;
import com.lee.bean.MainConfig2;
import com.lee.entity.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

public class TestSpring {


    @Test
    public void test() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        final Person person = context.getBean(Person.class);
        System.out.println(person);
        final String[] beanNames = context.getBeanNamesForType(Person.class);
        System.out.println(Arrays.toString(beanNames));

    }

    @Test
    public void test2() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        final Person person = context.getBean("person2", Person.class);
    }

    @Test
    public void test3() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        final String[] names = context.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
        final ConfigurableEnvironment environment = context.getEnvironment();
        final String osName = environment.getProperty("os.name");
        System.out.println(osName);
    }


    @Test
    public void test4(){
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        printAllBeans(context);
    }


    @Test
    public void test5(){
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
        final Object c1 = context.getBean("color2"); // 获取bean对象
        final Object c2 = context.getBean("&color2"); // 获取工厂对象
        System.out.println(c1.getClass());
        System.out.println(c2.getClass());

    }

    private void printAllBeans(AnnotationConfigApplicationContext context){
        final String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
