package com.spring.aop;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class TestProxy {


    @Test
    public void test() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //将动态生成的代理类保存下来
        Properties properties = System.getProperties();
        properties.put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");


        //目标对象
        Calculator target = new CalculatorImpl();

        //获取代理对象
//        final Object obj = new CalculatorProxy(target).getProxy();

        final Object obj = new CalculatorProxy2(target).getProxy();

        //转回具体对象
        Calculator proxy = (Calculator) obj;

        //调用方法
        final int add = proxy.div(1, 3);

        System.out.println(add);

        // 1代理对象能否转化为目标对象
        // 2代理队形调用代理方法 为什么会执行InvocationHandler的invoke方法
    }
}

/**
 * 模拟动态代理生成的代理类
 */
