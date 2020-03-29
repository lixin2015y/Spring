package com.spring.aop;


import java.awt.event.WindowFocusListener;
import java.lang.reflect.*;
import java.util.Arrays;

/**
 * 生辰代理对象
 * Proxy： 所有动态代理类父类，专门生成动态代理类和代理对象
 * 1）public static Class<?> getProxyClass(ClassLoader loader,Class<?>... interfaces) ---用于获取代理类Class对象
 * 2） public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)-----用于生成代理类的
 * Invocationhandler： 完成动态代理的整个过程
 * 1） invoke()方法
 */
public class CalculatorProxy2 {


    // 目标对象
    private Calculator target;


    public CalculatorProxy2(Calculator target) {
        this.target = target;
    }


    // 如何获取代理对象
    public Object getProxy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //代理对象
        Object proxy;

        //使用目标对象的类加载器
        ClassLoader loader = target.getClass().getClassLoader();

        Class[] interfaces = target.getClass().getInterfaces();


        final Class<?> cl = Proxy.getProxyClass(loader, interfaces);

        final Constructor<?> constructor = cl.getConstructor(InvocationHandler.class);

        proxy = constructor.newInstance((InvocationHandler) (proxy12, method, args) -> {
            // 将方法调用转回到目标对象
            final Object result = method.invoke(target, args);//目标对象执行目标方法

            //记录日志
            System.out.println("logger2===>" + method.getName() + "参数22====>" + Arrays.asList(args));

            return result;
        });

        return proxy;
    }
}
