package com.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.lee.service.DemoService;
import org.junit.Test;
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

    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        ApplicationConfig applicationConfig = context.getBean(ApplicationConfig.class);
        RegistryConfig registryConfig = context.getBean(RegistryConfig.class);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setInterface("com.lee.service.DemoService");
        reference.setGeneric(true);

        GenericService genericService = reference.get();
        Object result = genericService.$invoke("sayHello", new String[] {"java.lang.String"}, new Object[] {"111lixin"});
        System.out.println(result);
    }

}
