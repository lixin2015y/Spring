package com.lee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
        for (int i = 0; i < run.getBeanDefinitionNames().length; i++) {
            System.out.println(run.getBeanDefinitionNames()[i]);
        }
    }
}
