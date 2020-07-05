package com.lee.aop;

import org.springframework.stereotype.Service;

@Service
public class Calculator {

    public int div(int i, int j) {
        return i / j;
    }

}
