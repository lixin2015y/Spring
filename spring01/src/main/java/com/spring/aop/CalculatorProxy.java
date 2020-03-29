package com.spring.aop;


import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 生辰代理对象
 * Proxy： 所有动态代理类父类，专门生成动态代理类和代理对象
 * 1）public static Class<?> getProxyClass(ClassLoader loader,Class<?>... interfaces) ---用于获取代理类Class对象
 * 2） public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)-----用于生成代理类的
 * Invocationhandler： 完成动态代理的整个过程
 * 1） invoke()方法
 */
public class CalculatorProxy {


    // 目标对象
    private Calculator target;


    public CalculatorProxy(Calculator target) {
        this.target = target;
    }


    // 如何获取代理对象
    public Object getProxy() {
        //代理对象
        Object proxy;

        //使用目标对象的类加载器
        ClassLoader loader = target.getClass().getClassLoader();

        Class[] interfaces = target.getClass().getInterfaces();

        // 代理做什么
        /**
         * loader： 类加载器 帮我们加载动态代理生成的类
         * interfaces： 提供目标对象的所有接口 目的是让代理对象保证和目标对象都有接口中的方法
         * invocationHandler: 代理对象调用代理方法，会回来调用invoke()
         */
        proxy = Proxy.newProxyInstance(loader, interfaces, (proxy1, method, args) -> {
            /**
             * proxy1 一般不会使用
             * method 正在被调用的方法
             * args  正在被调用的方法参数
             */

            // 将方法调用转回到目标对象
            final Object result = method.invoke(target, args);//目标对象执行目标方法

            //记录日志
            System.out.println("logger===>" + method.getName() + "参数====>" + Arrays.asList(args));

            return result;
        });
        return proxy;
    }
}
