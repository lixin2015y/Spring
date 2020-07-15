package com.lee.tx;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TxTest {


    @Test
    public void test() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        final UserService userService = context.getBean(UserService.class);
        userService.insert("333", "lixin");

    }
}
