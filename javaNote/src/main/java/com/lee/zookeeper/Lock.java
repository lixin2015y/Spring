package com.lee.zookeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @className: Lock
 * @author: li xin
 * @date: 2021-12-05
 **/
public class Lock {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            countDownLatch.await();
        }

        countDownLatch.countDown();

    }
}
