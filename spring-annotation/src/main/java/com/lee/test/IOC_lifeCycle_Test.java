package com.lee.test;

import com.lee.bean.MyBeanLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class IOC_lifeCycle_Test {


    @Test
    public void test() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyBeanLifeCycle.class);
        context.close();
    }

}
