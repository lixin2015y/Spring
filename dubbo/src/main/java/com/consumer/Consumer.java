package com.consumer;

import com.api.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        DemoService demoService = context.getBean("demoService", DemoService.class);
        System.out.println(demoService.sayHello("lixin"));
//        System.out.println("消费者启动。。。。。");
//        new Scanner(System.in).nextLine();
    }

}
