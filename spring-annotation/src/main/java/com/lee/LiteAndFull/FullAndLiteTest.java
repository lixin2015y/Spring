package com.lee.LiteAndFull;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @className: FullAndLiteTest
 * @author: li xin
 * @date: 2022-01-22
 **/
public class FullAndLiteTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FullConfig.class);
        System.out.println(context.getBean("fullConfig"));
        System.out.println("==================");

        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(LiteConfig.class);
        System.out.println(context2.getBean("liteConfig"));
    }
}
