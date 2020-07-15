package com.lee.ext;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExtTest {


    @Test
    public void test() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExtConfig.class);
    }
}
