package com.lee.jvm;

import java.util.concurrent.atomic.AtomicInteger;

public class CASTest {


    public static final AtomicInteger race = new AtomicInteger(0);



    //
    private static final int THREADS_COUNT = 20;


    public static void increase() {
        race.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 1) {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10000);
            Thread.yield();
        }


        System.out.println(race);


    }

}
