package com.lee.juc.CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class AllThreadStartAtSameTime {

    static int total = 0;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(1);
        CountDownLatch countDownLatchFinish = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    downLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i1 = 0; i1 < 1000; i1++) {
                    total++;
                }
                countDownLatchFinish.countDown();
            }).start();
        }
        downLatch.countDown();
        countDownLatchFinish.await();
        System.out.println(total);

    }
}
