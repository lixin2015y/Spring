package com.lee.cycle;

import org.springframework.stereotype.Component;

@Component
public class A {

    private B b;

    public void setB(B b) {
        this.b = b;
    }
}
