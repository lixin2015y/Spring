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

        /**
         * dubbo 启动原理
         * 注解@EnableDubbo指定一个包扫描的路径
         * 内部实现注解@EnableDubboConfiSerg和@DubboComponentScan
         * 注解@EnableDubboConfig通过引入一个类，完成两个任务
         *      1、解析配置文件，向容器中注入对应的ApplicationConfig等对象，
         *      2、生成与这个对象一一对应的一个beanPostProcessor，并指定那些属性是需要进行复制的，
         *          这个bean的后置处理器会定向处理对应的xxxConfig对象，并给各个属性赋值，
         *          例如dubbo.application.name=provider就会生成一个ApplicationConfig对象并有一个属性name
         * 注解@DubboComponentScan 主要是对ServiceBean和ReferenceBean的注入，通过指定的路径进行扫描，
         *  他在bean工厂后置处理器调用，和ConfigurationClassPostProcessor.class同理
         * 1、解析ServiceBean
         *  通过向容器中注入一个ServiceAnnotationBeanPostProcessor.class工厂后置处理器，实现@Service注解，
         *      向容器中注入一个ServiceBean对象，在解析@Servcie对象的时候还会把具体的实现类也注入进去
         * 2、解析ReferenceBean
         *      入口也是@import注解，向容器中调用ReferenceAnnotationBeanPostProcessor.class
         *      ReferenceAnnotationBeanPostProcessor 用于扫描所有@Reference注解标注的属性，
         *      也由他来进行注入，并在注入的时候生成ReferenceBean代理对象
         *
         */


    }

    @Configuration
    @EnableDubbo(scanBasePackages = "com.provider.impl")
    @PropertySource("classpath:/properties/provider.properties")
    static class ProviderConfiguration {

    }
}
