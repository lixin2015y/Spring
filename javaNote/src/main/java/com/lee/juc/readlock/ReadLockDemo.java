package com.lee.juc.readlock;

import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁降级过程
 */
public class ReadLockDemo {

    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();


        // 锁降级过程

        new Thread(() -> {
            writeLock.lock();
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("获取到写锁");
            }
        }).start();

        new Thread(() -> {
            writeLock.lock();
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("再次获取到写锁");
            }
        }).start();

        new Thread(() -> {
            readLock.lock();
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("获取到读锁");
            }
        }).start();

    }
}
