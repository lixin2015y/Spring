package com.lee.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @className: UserServiceProxy
 * @author: li xin
 * @date: 2021-10-14
 **/
public class UserServiceProxy implements InvocationHandler {

    private UserService service;

    public UserServiceProxy(UserService service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("目标方法之行前置");
        try {
            return method.invoke(service, args);
        } finally {
            System.out.println("目标方法执行后置");
        }
    }
}
