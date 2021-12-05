package com.lee.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class AQS {
    private static volatile int count;
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(AQS::count).start();
        new Thread(AQS::count).start();
        new Thread(AQS::count).start();
        System.out.println(count);
    }

    private static void count() {
        try {
            lock.lock();
            for (int i = 0; i < 10000; i++) {
                count++;
            }
        } finally {
            lock.unlock();
        }


    }
}
