package com.lee.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class HelloApplicationRunningListener implements SpringApplicationRunListener {

    public HelloApplicationRunningListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting() {
        System.out.println("HelloApplicationRunningListener>>>>starting()");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("HelloApplicationRunningListener>>>>environmentPrepared()");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("HelloApplicationRunningListener>>>>contextPrepared()");

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("HelloApplicationRunningListener>>>>contextLoaded()");

    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("HelloApplicationRunningListener>>>>started()");

    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("HelloApplicationRunningListener>>>>running()");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("HelloApplicationRunningListener>>>>failed()");
    }
}
