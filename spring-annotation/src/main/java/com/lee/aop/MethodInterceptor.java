package com.lee.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @className: MethodInterceptor
 * @author: li xin
 * @date: 2022-03-22
 **/
public class MethodInterceptor implements org.aopalliance.intercept.MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("这里是methodInterceptor的前面");
        Object proceed = invocation.proceed();
        System.out.println("这里是methodInterceptor的后面");
        return proceed;
    }
}
