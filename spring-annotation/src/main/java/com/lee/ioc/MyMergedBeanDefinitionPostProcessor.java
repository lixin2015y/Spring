package com.lee.ioc;

import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyMergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {

    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        System.out.println("调用MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition" + beanName);
    }

    public void resetBeanDefinition(String beanName) {
        System.out.println("调用MergedBeanDefinitionPostProcessor的resetBeanDefinition" + beanName);
    }
}
