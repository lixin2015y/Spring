package com.spi.service.impl;

import com.spi.service.Animal;

public class Cat implements Animal {
    @Override
    public void sayHello() {
        System.out.println("喵喵喵");
    }
}
