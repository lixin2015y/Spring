package com.lee.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @className: MyDestructionAwareBeanPostprocessor
 * @author: li xin
 * @date: 2022-01-22
 **/
@Component
public class MyDestructionAwareBeanPostprocessor implements DestructionAwareBeanPostProcessor {
    @Override
    public boolean requiresDestruction(Object bean) {
        System.out.println("调用DestructionAwareBeanPostProcessor的requiresDestruction方法" + bean.toString());
        return false;
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        System.out.println("调用DestructionAwareBeanPostProcessor的postProcessBeforeDestruction方法" + beanName);
    }
}
