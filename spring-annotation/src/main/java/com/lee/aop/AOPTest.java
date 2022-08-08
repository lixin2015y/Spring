package com.lee.aop;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {


    @Test
    public void test() throws ServiceException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        Calculator calculator = context.getBean("calculator", Calculator.class);
        calculator.div(1, 1);
        calculator.add(2, 3);
    }
}
