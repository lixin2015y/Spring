package com.lee.proxy.jdk;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IDaoImpl iDaoImpl = new IDaoImpl();
        Object proxyObject = Proxy.newProxyInstance(IDaoImpl.class.getClassLoader(), IDaoImpl.class.getInterfaces(), (proxy, method, args1) -> {
            System.out.println("这里是代理方法");
            System.out.println("method.getName() = " + method.getName());
            System.out.println("args = " + Arrays.toString(args1));
            return method.invoke(iDaoImpl, args1);
        });
        IDao idao = (IDao) proxyObject;
        idao.select();
    }
}
