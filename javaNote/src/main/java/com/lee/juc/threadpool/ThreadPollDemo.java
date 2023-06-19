package com.lee.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPollDemo {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        executorService2.shutdownNow();

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println("==========================");
        for (int i = 0; i < 10; i++) {
            executorService1.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println("==========================");
        for (int i = 0; i < 30; i++) {
            executorService2.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

    }
}
