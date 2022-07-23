package com.lee.juc.lock;

import sun.security.krb5.internal.Ticket;

import java.util.concurrent.locks.ReentrantLock;


public class LockSaleTicket {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);

        Thread aaThread = new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "开始执行");
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }, "AA");

        Thread bbThread = new Thread(() -> {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("异常");
            }
            System.out.println(Thread.currentThread().getName() + "开始执行");
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "开始执行");
            }
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }, "BB");
        Thread ccThread = new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "开始执行");
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }, "CC");


        aaThread.start();
        Thread.sleep(10);
        bbThread.start();
        Thread.sleep(1000);
        ccThread.start();
        System.out.println("123123");
        bbThread.interrupt();
    }
}
