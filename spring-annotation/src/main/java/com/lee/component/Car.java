package com.lee.component;

public class Car {


    public Car() {
        System.out.println("调用构造方法");
    }

    public void init() {
        System.out.println("初始化");
    }

    public void destroy() {
        System.out.println("销毁");
    }

}
