package com.spring.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

public class MyBeanPostProcessor implements BeanPostProcessor {


    /**
     * 初始化方法之前调用
     *
     * @param bean     正在被创建的bean对象
     * @param beanName
     * @return 可做一些属性信息的校验
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("调用postProcessBeforeInitialization-----" + beanName);
        return bean;
    }

    /**
     * 初始化方法之后调用
     *
     * @param bean     正在被创建的bean对象
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization -----" + beanName);
        return bean;
    }
}
