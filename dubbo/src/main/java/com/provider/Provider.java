package com.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Provider {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();
        System.out.println("生产者启动。。。。。");
        new Scanner(System.in).nextLine();
    }
}
