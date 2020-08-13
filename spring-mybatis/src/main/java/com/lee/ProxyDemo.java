package com.lee;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Test {
    void say();
}

public class ProxyDemo {

    public static void main(String[] args) {
        Test test = new Test() {
            public void say() {
                Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(), new Class[]{Test.class}, new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        return null;
                    }
                });
            }
        };
    }
}
