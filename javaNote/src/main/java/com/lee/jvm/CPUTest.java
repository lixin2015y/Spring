package com.lee.jvm;

import com.lee.dto.User;
import org.checkerframework.checker.units.qual.C;

public class CPUTest {
    public static final int initData = 666;

    public int compute() {  //一个方法对应一块栈帧内存区域
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        CPUTest math = new CPUTest();
        while (true){
            math.compute();
        }
    }
}
