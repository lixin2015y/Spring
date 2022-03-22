package com.lee.aop;

import org.springframework.stereotype.Service;

@Service
public class CalculatorImpl implements Calculator {

    public int div(int i, int j) {
        System.out.println("执行了目标方法");
        return i / j;
    }

    public int add(int i, int j) {
        System.out.println("调动了加法");
        return i + j;
    }

}
