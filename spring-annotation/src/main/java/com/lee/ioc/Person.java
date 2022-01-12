package com.lee.ioc;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Person implements InitializingBean, DisposableBean {

    private String name;

    private Car car;

    public Person() {
        System.out.println("Person的构造方法");
    }

    public Person(String name, Car car) {
        System.out.println("Person(String name, Car car)的构造方法");
        this.name = name;
        this.car = car;
    }

    public void init() {
        System.out.println("person的init方法");
    }

    public void destroyProperty() throws Exception {
        System.out.println("bean属性指定的destroy方法");
    }

    @PostConstruct
    public void initPostConstruct() {
        System.out.println("被@PostConstruct标注的init方法");
    }

    @PreDestroy
    public void destroyPreDestroy() {
        System.out.println("被@PreDestroy标注的destroy方法");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("person实现接口的init方法");
    }

    public void destroy() throws Exception {
        System.out.println("person实现接口的destroy方法");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", car=" + car +
                '}';
    }
}
