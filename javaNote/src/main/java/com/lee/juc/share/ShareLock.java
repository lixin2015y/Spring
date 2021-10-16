package com.lee.juc.share;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间的通信
 *
 * @className: Share
 * @author: li xin
 * @date: 2021-10-16
 **/
class Resource {
    private int number = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public void incr() throws InterruptedException {
        lock.lock();
        try {
            // 这里的while如果替换成if会出现虚假唤醒现象
            while (number != 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + String.format("当前值：%d，操作后:%d", number++, number));
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }


    public void decr() throws InterruptedException {
        lock.lock();
        try {
            // 这里的while如果替换成if会出现虚假唤醒现象
            while (number != 1) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + String.format("当前值：%d，操作后:%d", number--, number));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class ShareLock {
    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    resource.incr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    resource.decr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BB").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    resource.incr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "CC").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    resource.decr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "DD").start();
    }
}
