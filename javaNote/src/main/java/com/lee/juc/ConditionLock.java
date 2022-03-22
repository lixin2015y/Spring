package com.lee.juc;

import org.terracotta.offheapstore.storage.IntegerStorageEngine;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLock {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Condition isEmpty = lock.newCondition();
        Condition isFull = lock.newCondition();

        LinkedList<Integer> deque = new LinkedList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.lock();
                    if (deque.size() >= 10) {
                        try {
                            isFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    deque.offer(finalI);
                    isEmpty.signal();
                    System.out.println(deque);
                    lock.unlock();
                }
            });

            thread.start();
        }

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.lock();
                    if (deque.size() <= 0) {
                        try {
                            isEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    deque.pollLast();
                    isFull.signal();
                    System.out.println(deque);
                    lock.unlock();

                }
            });
            thread.start();
        }
        countDownLatch.countDown();
    }
}
