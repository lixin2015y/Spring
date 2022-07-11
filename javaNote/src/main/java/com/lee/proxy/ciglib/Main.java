package com.lee.proxy.ciglib;

import com.lee.proxy.jdk.IDao;
import com.lee.proxy.jdk.IDaoImpl;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        //将代理类存到本地磁盘
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./");
        //实例化增强器
        Enhancer enhancer = new Enhancer();
        //设置需要代理的目标类
        enhancer.setSuperclass(IDaoImpl.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return method.invoke(o, objects);
            }
        });
        IDao iDao = (IDao)enhancer.create();
        iDao.select();

    }
}
