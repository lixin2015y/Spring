package com.lee.juc.deadLock;

public class DeadLock {

    private static Object a = new Object();
    private static Object b = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + "持有锁A，获取锁B");
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + "获取到锁B");
                }
            }
        }, "AA").start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + "持有锁B，获取锁A");
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + "获取到锁A");
                }
            }
        }, "BB").start();

    }
}
