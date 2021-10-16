package com.lee.juc.share;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: ThreadDemo3Resource
 * @author: li xin
 * @date: 2021-10-16
 **/
public class ThreadDemo3Resource {
    // 1 aa,2 bb,3 cc
    private int signal = 1;

    private Lock lock = new ReentrantLock();
    // 创建三个condition
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(int count) {
        lock.lock();
        try {
            while (signal != 1) {
                c1.await();
            }
            System.out.println(Thread.currentThread().getName() + ":" + count);
            signal = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(int count) {
        lock.lock();
        try {
            while (signal != 2) {
                c2.await();
            }
            System.out.println(Thread.currentThread().getName() + ":" + count);
            signal = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(int count) {
        lock.lock();
        try {
            while (signal != 3) {
                c3.await();
            }
            System.out.println(Thread.currentThread().getName() + ":" + count);
            signal = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 让三个线程按照顺序打印，这里使用test方法不行，不知道为什么
     * @param args
     */
    public static void main(String[] args) {
        ThreadDemo3Resource resource = new ThreadDemo3Resource();
        new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                resource.print5(i);
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                resource.print10(i);
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 15; i++) {
                resource.print15(i);
            }
        }, "CC").start();
    }

}
