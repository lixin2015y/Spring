package com.lee.entity;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;


public class Person implements BeanFactoryPostProcessor, BeanDefinitionRegistryPostProcessor {

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

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("调用Person实现BeanFactoryPostProcessor接口的postProcessBeanFactory");
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("调用Person实现BeanDefinitionRegistryPostProcessor接口的postProcessBeanDefinitionRegistry");
    }
}
