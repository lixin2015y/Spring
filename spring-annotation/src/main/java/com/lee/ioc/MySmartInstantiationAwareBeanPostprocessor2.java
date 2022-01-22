package com.lee.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.lang.reflect.Constructor;

/**
 * @className: MySmartInstantiationAwareBeanPostprocessor2
 * @author: li xin
 * @date: 2022-01-22
 **/
public class MySmartInstantiationAwareBeanPostprocessor2 implements SmartInstantiationAwareBeanPostProcessor {
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("调用SmartInstantiationAwareBeanPostProcessor的determineCandidateConstructors方法" + beanName);
        return new Constructor[0];
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        System.out.println("调用SmartInstantiationAwareBeanPostProcessor的getEarlyBeanReference方法" + beanName);
        return bean;
    }
}
