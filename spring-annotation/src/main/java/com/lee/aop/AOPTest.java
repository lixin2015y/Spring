package com.lee.aop;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {


    @Test
    public void test() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        final Calculator calculator = context.getBean(Calculator.class);
        calculator.div(1, 2);
    }
}
