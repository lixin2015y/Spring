package com.lee.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * TODO
 *
 * @className: DemoProxy
 * @author: li xin
 * @date: 2021-10-09
 **/
public class DemoProxy {
    public static void main(String[] args) {

        DemoInterface newProxyInstance = (DemoInterface) Proxy.newProxyInstance(DemoInterface.class.getClassLoader(), new Class[]{DemoInterface.class}, (proxy, method, args1) -> {
            System.out.println("调用代理对象方法，传入参数" + Arrays.toString(args1));
            return method.invoke(proxy, args1);
        });

        newProxyInstance.sayHello("li xin");
    }
}

interface DemoInterface {
    String sayHello(String name);
}

