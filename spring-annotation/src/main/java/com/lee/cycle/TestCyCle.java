package com.lee.cycle;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestCyCle {

    @Test
    public void test() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(CycleConfig.class);
    }
}
