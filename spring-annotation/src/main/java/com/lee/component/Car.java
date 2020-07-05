package com.lee.component;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Car implements InitializingBean, DisposableBean {


    public Car() {
        System.out.println("调用Car的构造构造方法");
    }


    public void userDefinedDestroy() {
        System.out.println("调用类内的userDefinedDestroy");
    }

    public void userDefinedInit() {
        System.out.println("调用类内的userDefinedInit");
    }

    public void destroy() throws Exception {
        System.out.println("调用DisposableBean接口的destroy");
    }

    public void afterPropertiesSet()  {
        System.out.println("调用InitializingBean的afterPropertiesSet");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("调用@PostConstruct标注的初始化方法");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("调用@PreDestroy标注的销毁方法");
    }




}
