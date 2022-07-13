package com.lee.juc.CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class AllThreadStartAtSameTime {

    static int total = 0;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    downLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        downLatch.countDown();
        System.out.println(total);

    }
}
