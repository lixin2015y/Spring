package com.lee.entity;

import org.springframework.beans.factory.annotation.Value;

public class Person {

    @Value("zhansanvalue")
    private String name;

    @Value("${age}")
    private Integer age;

    public Person() {
        System.out.println("调用构造方法");
    }

    public Person(String name, Integer age) {
        System.out.println("调用构造方法");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
