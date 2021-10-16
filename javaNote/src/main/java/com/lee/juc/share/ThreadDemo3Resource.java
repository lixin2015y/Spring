package com.lee.juc.share;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: ThreadDemo3Resource
 * @author: li xin
 * @date: 2021-10-16
 **/
public class ThreadDemo3Resource {
    // 1 aa,2 bb,3 cc
    private int signal;

    private ReentrantLock lock = new ReentrantLock();
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
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + count);
            }
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
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + count);
            }
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
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + count);
            }
            signal = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }



}
