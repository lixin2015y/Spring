package com.lee.component;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Cat implements InitializingBean, DisposableBean {


    public Cat() {
        System.out.println("构造方法");
    }

    public void destroy() throws Exception {
        System.out.println("调用Cat启动方法");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("调用Cat销毁方法");
    }
}
