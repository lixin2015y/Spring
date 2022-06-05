package com.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Provider {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();
        System.out.println("生产者启动。。。。。");
        new Scanner(System.in).nextLine();
    }

    @Test
    public void test(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        System.out.println("生产者启动。。。。。");
        new Scanner(System.in).nextLine();

    }

    @Configuration
    @EnableDubbo(scanBasePackages = "com.provider.impl")
    @PropertySource("classpath:/properties/provider.properties")
    static class ProviderConfiguration {

    }
}
