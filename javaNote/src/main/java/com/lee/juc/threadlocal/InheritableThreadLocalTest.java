package com.lee.juc.threadlocal;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class InheritableThreadLocalTest {
    public static void main(String[] args) {
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal();
        inheritableThreadLocal.set("lixin");
        new Thread(() -> {
            System.out.println(inheritableThreadLocal.get());
            new Thread(() -> {
                System.out.println("inheritableThreadLocal.get() = " + inheritableThreadLocal.get());
            }).start();
        }).start();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            System.out.println("inheritableThreadLocal.get() = " + inheritableThreadLocal.get());
        });
    }
}
