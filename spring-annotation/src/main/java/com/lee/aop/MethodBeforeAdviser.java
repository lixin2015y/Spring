package com.lee.aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MethodBeforeAdviser implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println(String.format("代理，执行方法[{%s}]", method.getName()));
    }
}
