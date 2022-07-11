package com.lee.proxy.ciglib;

import com.lee.proxy.jdk.IDao;
import com.lee.proxy.jdk.IDaoImpl;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        //将代理类存到本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./");
        //实例化增强器
        Enhancer enhancer = new Enhancer();
        //设置需要代理的目标类
        enhancer.setSuperclass(IDaoImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before");
                Object o1 = methodProxy.invokeSuper(o, objects);
                System.out.println("after" + o1);
                return "123123";
            }
        });
        IDaoImpl iDao = (IDaoImpl)enhancer.create();
        iDao.select();

    }
}
