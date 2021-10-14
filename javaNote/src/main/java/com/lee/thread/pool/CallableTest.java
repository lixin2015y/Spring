package com.lee.thread.pool;

import java.util.concurrent.Callable;

public class CallableTest implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println("继承了callable的call方法");
        return ":";
    }

    public static void main(String[] args) {
        new Thread().start();
        CallableTest callableTest = new CallableTest();
    }
}
